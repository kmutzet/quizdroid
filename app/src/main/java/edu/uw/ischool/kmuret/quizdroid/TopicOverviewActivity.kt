package edu.uw.ischool.kmuret.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Button

class TopicOverviewActivity : AppCompatActivity() {

    private var selectedTopic: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        selectedTopic = intent.getStringExtra("selectedTopic")  // Use "selectedTopic" instead of "chosenTopic"
        Log.d("TopicOverviewActivity", "Retrieved selectedTopic from intent: $selectedTopic")
        val nextSelectedTopic = selectedTopic?.let { QuizApp.repository.getTopicByName(it) }
        Log.d("TopicOverviewActivity", "Fetched topic from repository: $nextSelectedTopic")
        if (nextSelectedTopic != null) {
            Log.d("TopicOverviewActivity", "Topic title: ${nextSelectedTopic.title}")
            Log.d("TopicOverviewActivity", "Topic description: ${nextSelectedTopic.shortDescription}")
            Log.d("TopicOverviewActivity", "Total questions: ${nextSelectedTopic.quizzes.size}")
        } else {
            Log.e("TopicOverviewActivity", "Error: nextSelectedTopic is null!")
        }
        val tvTopicName: TextView = findViewById(R.id.tvTopicName)
        val tvTopicDescription: TextView = findViewById(R.id.tvTopicDescription)
        val tvTotalQuestions: TextView = findViewById(R.id.tvTotalQuestions)
        val btnBegin: Button = findViewById(R.id.btnBegin)

        tvTopicName.text = nextSelectedTopic?.title
        tvTopicDescription.text = nextSelectedTopic?.shortDescription
        tvTotalQuestions.text = getString(R.string.total_questions_text, nextSelectedTopic?.quizzes?.size ?: 0)

        btnBegin.setOnClickListener {
            Log.d("TopicOverviewActivity", "Begin button clicked. Starting QuestionActivity.")
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopic)
            intent.putExtra("questionInd", 0)
            intent.putExtra("count", 0)
            startActivity(intent)
            finish()
        }
    }
}