package edu.uw.ischool.kmuret.quizdroid
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.activity.addCallback
import android.widget.TextView
import android.widget.Button

class AnswerActivity : AppCompatActivity() {

    private var selectedTopic: String? = null
    private var questionInd: Int = 0
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        selectedTopic = intent.getStringExtra("selectedTopic")
        questionInd = intent.getIntExtra("questionInd", 0)
        val selectedOption = intent.getIntExtra("selectedOption", -1)
        count = intent.getIntExtra("count", 0)

        val tvYourAnswer: TextView = findViewById(R.id.tvYourAnswer)
        val tvCorrectAnswer: TextView = findViewById(R.id.tvCorrectAnswer)
        val tvScore: TextView = findViewById(R.id.tvScore)
        val btnNextOrFinish: Button = findViewById(R.id.btnNextOrFinish)

        val topic = selectedTopic?.let { QuizApp.repository.getTopicByName(it) }
        val quiz = topic?.quizzes?.get(questionInd)
        val correctAnswerInd = quiz?.correctAnswer ?: -1
        val correctAnswer = quiz?.options?.get(correctAnswerInd)

        tvYourAnswer.text = getString(R.string.your_answer, quiz?.options?.get(selectedOption))
        tvCorrectAnswer.text = getString(R.string.correct_answer, correctAnswer)
        tvScore.text = getString(R.string.score_count, count, questionInd + 1)

        val isNextQuestionAvailable: Boolean
        if ((questionInd + 1) < (topic?.quizzes?.size ?: 0)) {
            isNextQuestionAvailable = true
        } else {
            isNextQuestionAvailable = false
        }

        if (isNextQuestionAvailable) {
            btnNextOrFinish.text = getString(R.string.btn_next)
            btnNextOrFinish.setOnClickListener {
                val intent = Intent(this, QuestionActivity::class.java).apply {
                    putExtra("selectedTopic", selectedTopic)
                    putExtra("questionInd", questionInd + 1)
                    putExtra("count", count)
                }
                startActivity(intent)
                finish()
            }
        } else {
            btnNextOrFinish.text = getString(R.string.btn_finish)
            btnNextOrFinish.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        edge()
    }

    private fun edge() {
        onBackPressedDispatcher.addCallback(this) {
            val intent = Intent(this@AnswerActivity, QuestionActivity::class.java).apply {
                putExtra("selectedTopic", selectedTopic)
                putExtra("questionInd", questionInd)
                putExtra("count", count)
            }
            startActivity(intent)
            finish()
        }
    }
}