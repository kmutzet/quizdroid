package edu.uw.ischool.kmuret.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.TextView
import android.widget.Button

class TopicOverviewActivity : AppCompatActivity() {

    private var selectedTopic: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        selectedTopic = intent.getStringExtra("selectedTopic")
        val nextSelectedTopic = selectedTopic?.let { QuizApp.repository.getTopicByName(it) }

        val tvTopicName: TextView = findViewById(R.id.tvTopicName)
        val tvTopicDescription: TextView = findViewById(R.id.tvTopicDescription)
        val tvTotalQuestions: TextView = findViewById(R.id.tvTotalQuestions)
        val btnBegin: Button = findViewById(R.id.btnBegin)

        tvTopicName.text = nextSelectedTopic?.title
        tvTopicDescription.text = nextSelectedTopic?.shortDescription
        tvTotalQuestions.text = getString(R.string.total_questions_text, nextSelectedTopic?.quizzes?.size ?: 0)

        btnBegin.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopic)
            intent.putExtra("questionInd", 0)
            intent.putExtra("count", 0)
            startActivity(intent)
            finish()
        }
    }
}