package edu.uw.ischool.kmuret.quizdroid
data class SingleQuestion (
    val text: String,
    val options: List<String>,
    val correctAnswer: Int
)