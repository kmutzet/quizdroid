package edu.uw.ischool.kmuret.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
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

        val question = AllQuestions.topics[selectedTopic]?.questions?.get(questionInd)
        val correctAnswerInd = if (question != null) {
            question.correctAnswer
        } else {
            -1
        }

        val correctAnswer = if (question != null && correctAnswerInd > -1) {
            question.options[correctAnswerInd]
        } else {
            null
        }

        var selectedOptionIndex = -1
        var i = 0
        val radioButtonIds = arrayOf(R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.radioButton4)

        while (i < radioButtonIds.size) {
            if (selectedOption == radioButtonIds[i]) {
                selectedOptionIndex = i
                break
            }
            i++
        }


        tvYourAnswer.text = getString(R.string.your_answer, question?.options?.get(selectedOptionIndex))
        tvCorrectAnswer.text = getString(R.string.correct_answer, correctAnswer)
        tvScore.text = getString(R.string.score_count, count, questionInd + 1)

        val questions = AllQuestions.topics[selectedTopic]?.questions
        val questionSize = questions?.size ?: 0
        val isNextQuestionAvailable = (questionInd + 1) < questionSize

        if (isNextQuestionAvailable) {
            btnNextOrFinish.text = getString(R.string.btn_next)
            btnNextOrFinish.setOnClickListener {
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("selectedTopic", selectedTopic)
                intent.putExtra("questionInd", questionInd + 1)
                intent.putExtra("count", count)
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
            val intent = Intent(this@AnswerActivity, QuestionActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopic)
            intent.putExtra("questionInd", questionInd)
            intent.putExtra("count", count)
            startActivity(intent)
            finish()
        }
    }
}