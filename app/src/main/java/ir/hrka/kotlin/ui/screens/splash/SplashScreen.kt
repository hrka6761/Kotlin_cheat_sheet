package ir.hrka.kotlin.ui.screens.splash

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.hrka.kotlin.MainActivity
import ir.hrka.kotlin.R
import ir.hrka.kotlin.core.utilities.Constants.MANDATORY
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_CANCEL
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_NOT_AVAILABLE
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_CONTINUE
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_AVAILABLE
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_DOWNLOADING
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_DOWNLOAD_FAILED
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_INSTALLING
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_NOT_INSTALL_PERMISSION
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_UNKNOWN_STATE
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.core.utilities.Screen.Home
import kotlinx.coroutines.delay
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import ir.hrka.kotlin.core.utilities.Constants.NEW_VERSION_DOWNLOADED

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SplashScreen(activity: MainActivity, navHostController: NavHostController) {

    val viewModel: SplashViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }
    val appInfo by viewModel.appInfo.collectAsState()
    val newVersionState by viewModel.newVersionState.collectAsState()
    val installPermissionLauncher = rememberLauncherForActivityResult(
        StartActivityForResult()
    ) {
        if (viewModel.hasUnknownSourceInstallPermission(activity))
            viewModel.setNewVersionDialogState(NEW_VERSION_INSTALLING)
    }
    val installLauncher = rememberLauncherForActivityResult(StartActivityForResult()) {}


    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (logo, title, authorImg, authorName, progressBar, snackBar) = createRefs()

        Image(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
        )

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(logo.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = stringResource(R.string.splash_screen_title)
        )

        Text(
            modifier = Modifier
                .constrainAs(authorName) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                },
            text = stringResource(R.string.splash_screen_author_name),
            fontSize = 8.sp
        )

        CircularProgressIndicator(
            modifier = Modifier
                .width(35.dp)
                .height(35.dp)
                .alpha(
                    if (newVersionState == NEW_VERSION_AVAILABLE ||
                        newVersionState == NEW_VERSION_CONTINUE ||
                        newVersionState == NEW_VERSION_DOWNLOADING ||
                        newVersionState == NEW_VERSION_NOT_INSTALL_PERMISSION ||
                        newVersionState == NEW_VERSION_INSTALLING
                    ) 0f else 1f
                )
                .constrainAs(progressBar) {
                    bottom.linkTo(authorName.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            strokeWidth = 1.dp
        )

        Image(
            modifier = Modifier
                .width(25.dp)
                .height(25.dp)
                .constrainAs(authorImg) {
                    bottom.linkTo(authorName.top, margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = painterResource(R.drawable.author),
            contentDescription = null,
        )

        if (newVersionState == NEW_VERSION_AVAILABLE ||
            newVersionState == NEW_VERSION_CONTINUE ||
            newVersionState == NEW_VERSION_DOWNLOADING ||
            newVersionState == NEW_VERSION_NOT_INSTALL_PERMISSION
        )
            AlertDialog(
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = {},
                confirmButton = {
                    when (newVersionState) {
                        NEW_VERSION_AVAILABLE -> {
                            TextButton(
                                onClick = {
                                    viewModel.setNewVersionDialogState(NEW_VERSION_CONTINUE)
                                }
                            ) {
                                Text(
                                    text = stringResource(R.string.new_version_dialog_update_btn),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        NEW_VERSION_NOT_INSTALL_PERMISSION -> {
                            TextButton(
                                onClick = {
                                    val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                                        .apply {
                                            data = Uri.parse("package:${activity.packageName}")
                                        }
                                    installPermissionLauncher.launch(intent)
                                }
                            ) {
                                Text(
                                    text = stringResource(R.string.new_version_dialog_allow_btn),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                },
                dismissButton = {
                    if (newVersionState == NEW_VERSION_AVAILABLE ||
                        newVersionState == NEW_VERSION_NOT_INSTALL_PERMISSION
                    )
                        TextButton(
                            onClick = {
                                viewModel.setNewVersionDialogState(NEW_VERSION_CANCEL)
                            }
                        ) { Text(text = stringResource(R.string.new_version_dialog_cancel_btn)) }
                },
                icon = { Icon(painterResource(R.drawable.update), contentDescription = null) },
                title = {
                    when (newVersionState) {
                        NEW_VERSION_AVAILABLE -> Text(text = stringResource(R.string.new_version_dialog_title_update))
                        NEW_VERSION_CONTINUE -> Text(text = stringResource(R.string.new_version_dialog_title_downloading))
                        NEW_VERSION_NOT_INSTALL_PERMISSION -> Text(text = stringResource(R.string.new_version_dialog_title_install_permission))
                    }
                },
                text = {
                    when (newVersionState) {
                        NEW_VERSION_AVAILABLE -> {
                            Text(
                                stringResource(
                                    R.string.new_version_dialog_desc_available,
                                    if (appInfo.data?.versionNameSuffix == MANDATORY)
                                        stringResource(R.string.new_version_dialog_desc_available_mandatory_update)
                                    else
                                        ""
                                )
                            )
                        }

                        NEW_VERSION_CONTINUE -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(strokeWidth = 2.dp)
                            }
                        }

                        NEW_VERSION_NOT_INSTALL_PERMISSION -> {
                            Text(
                                stringResource(
                                    R.string.new_version_dialog_desc_install_permission,
                                    if (appInfo.data?.versionNameSuffix == MANDATORY)
                                        stringResource(R.string.new_version_dialog_desc_available_mandatory_update)
                                    else
                                        ""
                                )
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 16.dp
            )

        SnackbarHost(
            modifier = Modifier
                .constrainAs(snackBar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            hostState = snackBarHostState
        )
    }

    LaunchedEffect(appInfo) {
        when (appInfo) {
            is Resource.Initial -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                viewModel.checkNewVersion(activity)
            }

            is Resource.Error -> {
                snackBarHostState.showSnackbar(
                    message = appInfo.error?.errorMsg.toString(),
                    duration = SnackbarDuration.Long
                )
                viewModel.setNewVersionDialogState(NEW_VERSION_UNKNOWN_STATE)
            }
        }
    }

    LaunchedEffect(newVersionState) {
        if (newVersionState == NEW_VERSION_NOT_AVAILABLE || newVersionState == NEW_VERSION_UNKNOWN_STATE) {
            delay(1000)
            navHostController.navigate(Home())
        } else if (newVersionState == NEW_VERSION_CANCEL) {
            if (appInfo.data?.versionNameSuffix == MANDATORY)
                activity.finish()
            else
                navHostController.navigate(Home())
        } else if (newVersionState == NEW_VERSION_CONTINUE) {
            viewModel.downloadNewVersion(activity)
        } else if (newVersionState == NEW_VERSION_DOWNLOAD_FAILED) {
            snackBarHostState.showSnackbar(
                message = activity.getString(R.string.new_version_download_failed_msg),
                duration = SnackbarDuration.Long
            )
            delay(1000)
            navHostController.navigate(Home())
        } else if (newVersionState == NEW_VERSION_DOWNLOADED) {
            if (viewModel.hasUnknownSourceInstallPermission(activity)) {
                viewModel.setNewVersionDialogState(NEW_VERSION_INSTALLING)
            } else
                viewModel.setNewVersionDialogState(NEW_VERSION_NOT_INSTALL_PERMISSION)
        } else if (newVersionState == NEW_VERSION_INSTALLING) {
            val apkUri: Uri = FileProvider.getUriForFile(
                activity,
                "${activity.packageName}.provider",
                viewModel.getNewVersionApkFile()
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkUri, activity.getString(R.string.apk_type))
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            installLauncher.launch(intent)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAppInfo()
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(MainActivity(), rememberNavController())
}