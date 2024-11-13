package edu.uw.ischool.kmuret.quizdroid

interface TopicRepository {
    fun getTopicByName(name: String): Topic?
    fun getAllTopics(): List<Topic>
    fun addTopic(topic: Topic)
    suspend fun fetchTopics()

}