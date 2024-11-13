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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topicsListView: ListView = findViewById(R.id.topicListView)
        lifecycleScope.launch {
            QuizApp.repository.fetchTopics()
            val topicsList = QuizApp.repository.getAllTopics().map { it.title }
            withContext(Dispatchers.Main) {
                topicsListView.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, topicsList)
            }
        }

        topicsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedTopic = (topicsListView.adapter.getItem(position) as String)
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopic)
            startActivity(intent)
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
}