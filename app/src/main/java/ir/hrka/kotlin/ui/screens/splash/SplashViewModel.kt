package ir.hrka.kotlin.ui.screens.splash

import android.app.DownloadManager
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hrka.kotlin.MainActivity
import ir.hrka.kotlin.core.DownloadReceiver
import ir.hrka.kotlin.core.utilities.Constants.BASE_APK_PATH_URL
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_NOT_AVAILABLE
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_NO_STATE
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_AVAILABLE
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_DOWNLOADED
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_DOWNLOAD_FAILED
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_UNKNOWN_STATE
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.domain.entities.AppInfoModel
import ir.hrka.kotlin.domain.usecases.GetAppInfoUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SplashViewModel @Inject constructor(
    @Named("IO") private val io: CoroutineDispatcher,
    private val getAppInfoUseCase: GetAppInfoUseCase,
    private val downloadManager: DownloadManager
) : ViewModel() {

    private val _appInfo: MutableStateFlow<Resource<AppInfoModel?>> =
        MutableStateFlow(Resource.Initial())
    val appInfo: StateFlow<Resource<AppInfoModel?>> = _appInfo
    private val _newVersionState: MutableStateFlow<Int> =
        MutableStateFlow(NEW_VERSION_NO_STATE)
    val newVersionState: StateFlow<Int> = _newVersionState
    private lateinit var downloadReceiver: DownloadReceiver
    private val filter = IntentFilter().apply {
        addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
    }


    fun setNewVersionDialogState(state: Int) {
        _newVersionState.value = state
    }

    fun getAppInfo() {
        viewModelScope.launch(io) {
            _appInfo.value = Resource.Loading()
            _appInfo.value = getAppInfoUseCase()
        }
    }

    fun checkNewVersion(context: Context) {
        try {
            val packageInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            val versionCode = packageInfo.longVersionCode.toInt()
            if (_appInfo.value.data != null)
                if (versionCode < _appInfo.value.data!!.versionCode)
                    _newVersionState.value = NEW_VERSION_NOT_AVAILABLE
                else
                    _newVersionState.value = NEW_VERSION_AVAILABLE
        } catch (e: Exception) {
            _newVersionState.value = NEW_VERSION_UNKNOWN_STATE
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun downloadNewVersion(activity: MainActivity) {
        if (getNewVersionApkFile().exists()) {
            setNewVersionDialogState(NEW_VERSION_DOWNLOADED)
            return
        }

        val request = DownloadManager.Request(Uri.parse(getNewVersionApkFileUrl()))

        downloadReceiver = DownloadReceiver(
            downloadManager,
            successCallback = {
                activity.unregisterReceiver(downloadReceiver)
                setNewVersionDialogState(NEW_VERSION_DOWNLOADED)
            },
            failCallback = {
                activity.unregisterReceiver(downloadReceiver)
                setNewVersionDialogState(NEW_VERSION_DOWNLOAD_FAILED)
            }
        )
        activity.registerReceiver(downloadReceiver, filter, RECEIVER_EXPORTED)
        request
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle("Kotlin cheat sheet")
            .setDescription("Downloading new version of kotlin cheat sheet app.")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                getNewVersionApkFileName()
            )

        downloadManager.enqueue(request)
    }

    fun hasUnknownSourceInstallPermission(activity: MainActivity) =
        activity.packageManager.canRequestPackageInstalls()

    fun getNewVersionApkFile(): File =
        File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            getNewVersionApkFileName()
        )

    private fun getNewVersionApkFileName(): String =
        /*"Kotlin_Cheat_Sheet_V${_appInfo.value.data?.versionName}.apk"*/"v0.12.1-dariarnd.apk"

    private fun getNewVersionApkFileUrl(): String =
        BASE_APK_PATH_URL +/* "V" +*/ /*_appInfo.value.data?.versionName*/"/0.0.0/" + getNewVersionApkFileName()
}