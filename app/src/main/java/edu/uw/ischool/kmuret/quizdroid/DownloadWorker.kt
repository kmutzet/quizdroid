package edu.uw.ischool.kmuret.quizdroid

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class DownloadWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // Get download URL and send download attempt broadcast
            val sharedPreferences = applicationContext.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val urlString = sharedPreferences.getString("URL", "defaultURL") ?: "defaultURL"
            applicationContext.sendBroadcast(Intent("DOWNLOAD_ATTEMPT"))

            // Check network and airplane mode
            if (!isNetworkAvailable() || isAirplaneModeOn()) {
                return@withContext Result.retry()
            }

            // Download the file
            val tempFile = File(applicationContext.filesDir, "questions_temp.json")
            downloadFile(urlString, tempFile)

            // Rename to final file if download succeeds
            val finalFile = File(applicationContext.filesDir, "questions.json")
            if (!tempFile.renameTo(finalFile)) throw IOException("Failed to save downloaded file")

            Log.d("DownloadWorker", "Download complete and file saved")
            applicationContext.sendBroadcast(Intent("DATA_UPDATED"))

            Result.success()
        } catch (e: Exception) {
            Log.e("DownloadWorker", "Download error: ${e.message}")
            applicationContext.sendBroadcast(Intent("DOWNLOAD_FAILED"))
            Result.failure()
        }
    }

    private fun downloadFile(urlString: String, outputFile: File) {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            throw IOException("HTTP error code: ${connection.responseCode}")
        }

        connection.inputStream.use { inputStream ->
            FileOutputStream(outputFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        showNotification("Download Complete", "Data has been successfully downloaded.")
    }

    private fun showNotification(title: String, content: String) {
        val channelId = "Download_Channel"
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "Download Channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(applicationContext.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }
}