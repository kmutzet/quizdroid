package edu.uw.ischool.kmuret.quizdroid

data class Topic (
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val quizzes: List<Quiz>
)