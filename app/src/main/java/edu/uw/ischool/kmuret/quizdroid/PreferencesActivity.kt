package edu.uw.ischool.kmuret.quizdroid

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.NetworkType
import androidx.work.Constraints
import androidx.work.WorkRequest

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val etUrl: EditText = findViewById(R.id.etUrl)
        val etDownloadFrequency: EditText = findViewById(R.id.etDownloadFrequency)
        val btnSavePreferences: Button = findViewById(R.id.btnSavePreferences)
        //val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val sharedPreferences = applicationContext.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("URL", "https://tednewardsandbox.site44.com/questions.json")
        editor.apply()

        etUrl.setText(sharedPreferences.getString("URL", ""))
        etDownloadFrequency.setText(sharedPreferences.getInt("DownloadFrequency", 60).toString())

        btnSavePreferences.setOnClickListener {
            val url = etUrl.text.toString()
            val downloadFrequency = etDownloadFrequency.text.toString().toIntOrNull() ?: 60
            with(sharedPreferences.edit()) {
                putString("URL", url)
                Log.d("Preferences", "URL saved: $url")
                putInt("DownloadFrequency", downloadFrequency)
                apply()
            }

            Log.d("Preferences", "Preferences saved, initiating download worker")
            checkoutNetworkAndProceed() // Removed parameter here
        }
    }

    private fun checkoutNetworkAndProceed() {
        if (isAirplaneModeOn()) {
            promptForAirplaneMode()
        } else if (!isNetworkAvailable()) {
            Toast.makeText(this, "No network connection available", Toast.LENGTH_LONG).show()
        } else {
            Log.d("Preferences", "Network is available, enqueuing worker")
            initiateDownloadWorker() // Worker call with no arguments
            Toast.makeText(this, "Preferences saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun promptForAirplaneMode() {
        AlertDialog.Builder(this)
            .setTitle("Airplane Mode Active")
            .setMessage("Airplane mode is on. Do you want to disable it?")
            .setPositiveButton("Yes") { _, _ ->
                startActivity(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS))
            }
            .setNegativeButton("No", null)
            .show()
    }

// This function is to enqueue the worker immediately after saving preferences
    internal fun initiateDownloadWorker() {
        // Add logging to confirm the worker request is being built
        Log.d("Preferences", "Building download worker request")

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)  // Ensure there's a network connection
            .build()

        // Create a OneTimeWorkRequest for a one-time task
        val downloadRequest: WorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .build()

        // Enqueue the work request
        Log.d("Preferences", "Enqueuing download worker request")
        WorkManager.getInstance(this).enqueue(downloadRequest)
    }
}
