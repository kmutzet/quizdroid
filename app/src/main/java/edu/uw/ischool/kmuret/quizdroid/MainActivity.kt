package edu.uw.ischool.kmuret.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topicsListView: ListView = findViewById(R.id.topicListView)
        val topicsList = QuizApp.repository.getAllTopics().map { it.title }

        topicsListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topicsList)
        topicsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedTopic = topicsList[position]
            val intent = Intent(this, TopicOverviewActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopic)
            startActivity(intent)
        }
    }
}