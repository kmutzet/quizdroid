package edu.uw.ischool.kmuret.quizdroid
data class Quiz (
    val text: String,
    val options: List<String>,
    val correctAnswer: Int
)