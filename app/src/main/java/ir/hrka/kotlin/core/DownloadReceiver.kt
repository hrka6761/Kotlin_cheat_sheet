package ir.hrka.kotlin.core

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import ir.hrka.kotlin.core.utilities.Constants.TAG

class DownloadReceiver(
    private val downloadManager: DownloadManager,
    private val successCallback: (filePath: String) -> Unit,
    private val failCallback: () -> Unit
) : BroadcastReceiver() {

    @SuppressLint("Range")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val query = DownloadManager.Query().setFilterById(downloadId)
            val cursor = downloadManager.query(query)

            if (cursor.moveToFirst()) {
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    val fileUri =
                        Uri.parse(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)))
                    val filePath = fileUri.path
                    if (filePath != null)
                        successCallback(filePath)
                    else
                        failCallback()
                } else
                    failCallback()
            }
        }
    }
}