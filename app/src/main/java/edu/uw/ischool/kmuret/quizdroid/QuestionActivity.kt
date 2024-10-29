package edu.uw.ischool.kmuret.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.content.Intent
import androidx.activity.addCallback

class QuestionActivity : AppCompatActivity() {
    private var questionInd: Int = 0
    private var count: Int = 0
    private var selectedTopic: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        selectedTopic = intent.getStringExtra("selectedTopic")
        questionInd = intent.getIntExtra("questionInd", 0)
        count = intent.getIntExtra("count", 0)

        val question = AllQuestions.topics[selectedTopic]?.questions?.get(questionInd)

        val tvQuestion: TextView = findViewById(R.id.tvQuestion)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val radioButton1: RadioButton = findViewById(R.id.radioButton1)
        val radioButton2: RadioButton = findViewById(R.id.radioButton2)
        val radioButton3: RadioButton = findViewById(R.id.radioButton3)
        val radioButton4: RadioButton = findViewById(R.id.radioButton4)
        /*
         related to extra button tests
        val radioButton5: RadioButton = findViewById(R.id.radioButton5)
        val radioButton6: RadioButton = findViewById(R.id.radioButton6)
        val radioButton7: RadioButton = findViewById(R.id.radioButton7)
        val radioButton8: RadioButton = findViewById(R.id.radioButton8)
        val radioButton9: RadioButton = findViewById(R.id.radioButton9) */
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        tvQuestion.text = question?.text
        radioButton1.text = question?.options?.get(0)
        radioButton2.text = question?.options?.get(1)
        radioButton3.text = question?.options?.get(2)
        radioButton4.text = question?.options?.get(3)
        //testing extra buttons show up
        //radioButton5.text = question?.options?.get(4)
        //radioButton6.text = question?.options?.get(5)
        //radioButton7.text = question?.options?.get(6)
        //radioButton8.text = question?.options?.get(7)
        //       radioButton9.text = question?.options?.get(8)

        radioGroup.setOnCheckedChangeListener { _, _ ->
            btnSubmit.visibility = View.VISIBLE
        }

        btnSubmit.setOnClickListener {
            val selectedOption = radioGroup.checkedRadioButtonId
            val correctAnswerIndex = question?.correctAnswer ?: -1

            var isAnswerCorrect = false
            var i = 0
            val radioButtonIds = arrayOf(R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.radioButton4)

            while (i < 4) {
                if (selectedOption == radioButtonIds[i]) {
                    isAnswerCorrect = (correctAnswerIndex == i)
                    break // exit loop if the correct answer is found
                }
                i++
            }

            if (isAnswerCorrect) {
                count += 1
            }

            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopic)
            intent.putExtra("questionInd", questionInd)
            intent.putExtra("selectedOption", selectedOption)
            intent.putExtra("count", count)
            startActivity(intent)
            finish()
        }
        edge()
    }

    private fun edge() {
        if (questionInd > 0) {
            onBackPressedDispatcher.addCallback(this) {
                val intent = Intent(this@QuestionActivity, QuestionActivity::class.java)
                intent.putExtra("selectedTopic", selectedTopic)
                intent.putExtra("questionInd", questionInd - 1)
                intent.putExtra("count", count - 1)
                startActivity(intent)
                finish()
            }
        }
    }
}