package edu.uw.ischool.kmuret.quizdroid

data class Topic (
    val shortDescription: String,
    val longDescription: String,
    val quizzes: List<Quiz>,
    val title: String
)