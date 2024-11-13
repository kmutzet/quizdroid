package edu.uw.ischool.kmuret.quizdroid

import android.content.Context
import android.util.Log
import org.json.JSONArray
import java.io.File
import java.io.IOException

class CurrentTopicRepository(private val context: Context) : TopicRepository {

    private val topics = mutableListOf<Topic>()

    override fun getAllTopics(): List<Topic> = topics
    override fun getTopicByName(name: String): Topic? = topics.find { it.title == name }
    override fun addTopic(topic: Topic) {
        topics.add(topic)
    }

    override suspend fun fetchTopics() {
        try {
            val inputStream = context.assets.open("ecquestions.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)

            val jsonArray = JSONArray(jsonString)
            val newTopics = mutableListOf<Topic>()
            for (i in 0 until jsonArray.length()) {
                val jsonTopic = jsonArray.getJSONObject(i)
                val title = jsonTopic.getString("title")
                val shortDescription = jsonTopic.getString("desc")
                val longDescription = ""

                val jsonQuestions = jsonTopic.getJSONArray("questions")
                val quizzes = mutableListOf<Quiz>()

                for (j in 0 until jsonQuestions.length()) {
                    val jsonQuestion = jsonQuestions.getJSONObject(j)
                    val text = jsonQuestion.getString("text")
                    val answerIndex = jsonQuestion.getInt("answer")
                    val answers = mutableListOf<String>()

                    val jsonAnswers = jsonQuestion.getJSONArray("answers")
                    for (k in 0 until jsonAnswers.length()) {
                        answers.add(jsonAnswers.getString(k))
                    }
                    quizzes.add(Quiz(text, answers, answerIndex))
                }

                newTopics.add(Topic(title, shortDescription, longDescription, quizzes))
            }

            topics.clear()
            topics.addAll(newTopics)
        } catch (e: IOException) {
            Log.e("CurrentTopicRepository", "Error reading questions.json from assets", e)
        } catch (e: Exception) {
            Log.e("CurrentTopicRepository", "Error parsing JSON", e)
        }
    }
}
