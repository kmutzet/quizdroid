package edu.uw.ischool.kmuret.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent
import android.widget.TextView
import android.widget.Button

class TopicOverviewActivity : AppCompatActivity() {

    private var selectedTopicKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        selectedTopicKey = intent.getStringExtra("selectedTopic")
        val selectedTopicQuestions = AllQuestions.topics[selectedTopicKey]?.questions

        val tvTopicName: TextView = findViewById(R.id.tvTopicName)
        val tvTopicDescription: TextView = findViewById(R.id.tvTopicDescription)
        val tvTotalQuestions: TextView = findViewById(R.id.tvTotalQuestions)
        val btnBegin: Button = findViewById(R.id.btnBegin)

        tvTopicName.text = selectedTopicKey
        tvTopicDescription.text = AllQuestions.topics[selectedTopicKey]?.description
        tvTotalQuestions.text = getString(R.string.total_questions_text, selectedTopicQuestions?.size ?: 0)

        btnBegin.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopicKey)
            intent.putExtra("questionInd", 0)
            intent.putExtra("count", 0)
            startActivity(intent)
            finish()
        }
    }
}