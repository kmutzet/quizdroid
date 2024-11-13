package edu.uw.ischool.kmuret.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.withContext
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var listViewTopics: ListView

    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            reloadTopicsList()
        }
    }
    private val errorReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showErrorDialog()
        }
    }
    private val downloadEventReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                "DOWNLOAD_INITIATED" -> showToastMessage("Download attempt started")
                "DATA_REFRESHED" -> showToastMessage("Download succeeded")
                "DOWNLOAD_ERROR" -> showToastMessage("Download failed")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewTopics = findViewById(R.id.topicListView)
        reloadTopicsList()

        listViewTopics.setOnItemClickListener { _, _, position, _ ->
            val chosenTopic = (listViewTopics.adapter.getItem(position) as String)
            val intent = Intent(this, TopicOverviewActivity::class.java).apply {
                putExtra("chosenTopic", chosenTopic)
            }
            startActivity(intent)
        }

        registerReceiver(updateReceiver, IntentFilter("DATA_REFRESHED"))
        registerReceiver(errorReceiver, IntentFilter("DOWNLOAD_ERROR"))
        IntentFilter().apply {
            addAction("DOWNLOAD_INITIATED")
            addAction("DATA_REFRESHED")
            addAction("DOWNLOAD_ERROR")
        }.also { filter -> registerReceiver(downloadEventReceiver, filter) }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        reloadTopicsList()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(updateReceiver)
        unregisterReceiver(errorReceiver)
        unregisterReceiver(downloadEventReceiver)
    }

    private fun reloadTopicsList() {
        lifecycleScope.launch {
            QuizApp.repository.fetchTopics()
            val topicTitles = QuizApp.repository.getAllTopics().map { it.title }
            withContext(Dispatchers.Main) {
                listViewTopics.adapter =
                    ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, topicTitles)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_preferences -> {
                startActivity(Intent(this, PreferencesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("Download Failed")
            .setMessage("An error occurred while downloading the data. Do you want to retry or exit?")
            .setPositiveButton("Retry") { _, _ ->
                attemptDownloadAgain()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }
            .show()
    }

    private fun attemptDownloadAgain() {
        val sharedPrefs = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val downloadInterval = sharedPrefs.getInt("DownloadInterval", 60)
        PreferencesActivity().initiateDownloadWorker(downloadInterval)
    }
}
