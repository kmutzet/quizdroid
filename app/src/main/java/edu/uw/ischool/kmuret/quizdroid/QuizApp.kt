package edu.uw.ischool.kmuret.quizdroid

import android.app.Application
import android.util.Log

class QuizApp : Application() {
    companion object {
        lateinit var repository: TopicRepository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        repository = CurrentTopicRepository(this)
        Log.i("QuizApp", "fileDir=$filesDir")
        Log.d("QuizApp", "QuizApp is running")
    }
}