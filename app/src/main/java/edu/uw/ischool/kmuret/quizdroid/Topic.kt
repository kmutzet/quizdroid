package edu.uw.ischool.kmuret.quizdroid

data class Topic (
    val description: String,
    val questions: List<SingleQuestion>
)