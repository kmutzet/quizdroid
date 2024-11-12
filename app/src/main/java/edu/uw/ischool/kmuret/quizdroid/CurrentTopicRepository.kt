package edu.uw.ischool.kmuret.quizdroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.activity.addCallback
import android.widget.TextView
import android.widget.Button

class CurrentTopicRepository : TopicRepository {

    private val topics = mutableListOf(
        Topic(
            title = "Math",
            shortDescription = "Math questions.",
            longDescription = "A series of questions tackling the basics of Mathematics.",
            quizzes = listOf(
                Quiz("What is the square root of 16?", listOf("2", "4", "8", "16"), 1),
                Quiz("What is 12 * 12?", listOf("122", "132", "144", "156"), 2),
                Quiz("What is the value of π (pi) rounded to 2 decimal places?", listOf("3.14", "2.71", "1.61", "3.33"), 0),
                Quiz("What is the derivative of x^2?", listOf("x", "2x", "x^2", "2x^2"), 1),
                Quiz("What is the integral of 1/x?", listOf("ln(x)", "1/x^2", "x^2", "x^3/3"), 0),
                Quiz("What is 7 + (6 * 5^2 - 3)?", listOf("100", "103", "170", "148"), 1),
                Quiz("What is 7 + (6 * 5^2 - 3)?", listOf("100", "103", "170", "148"), 1)
            )
        ),
        Topic(
            title = "Physics",
            shortDescription = "Physics questions.",
            longDescription = "A series of questions tackling the fundamental concepts of Physics.",
            quizzes = listOf(
                Quiz("What is the speed of light in a vacuum?", listOf("3x10^5 m/s", "3x10^8 m/s", "3x10^3 m/s", "3x10^6 m/s"), 1),
                Quiz("Who is known as the father of modern physics?", listOf("Isaac Newton", "Albert Einstein", "Galileo Galilei", "Niels Bohr"), 2),
                Quiz("What type of particle is an electron?", listOf("Quark", "Neutron", "Lepton", "Boson"), 2),
                Quiz("What is the unit of electrical resistance?", listOf("Joule", "Volt", "Ohm", "Watt"), 2),
                Quiz("What is the first law of thermodynamics about?", listOf("Energy conservation", "Entropy", "Action and Reaction", "Gravity"), 0),
                Quiz("What is the formula for calculating kinetic energy?", listOf("mv^2", "1/2 mv^2", "ma", "1/2 ma"), 1),
                Quiz("Who discovered radioactivity?", listOf("Marie Curie", "Albert Einstein", "Isaac Newton", "Enrico Fermi"), 0)
            )
        ),
        Topic(
            title = "Marvel Super Heroes",
            shortDescription = "Marvel Super Heroes questions.",
            longDescription = "Fun questions about Marvel Super Heroes and the marvel cinematic universe.",
            quizzes = listOf(
                Quiz("Which state is Star Lord (Peter Quill) from", listOf("Kentucky", "Indiana", "Missouri", "Tennessee"),2),
                Quiz("What is Thor's nickname?", listOf("Strongest Avenger", "Point Break", "God of Thunder", "God of Hammers"),1),
                Quiz("Who said the famous quote, 'Why is Gamora?'", listOf("Dr. Strange", "Tony Stark", "Peter Parker", "Drax"),3),
                Quiz("In Captain America Civil War, which of these characters is on Captain America's team?", listOf("Hulk", "Thor", "Wanda", "T'challa"),2),
                Quiz("What is Pepper Pots allergic to?", listOf("Wheat", "Peanuts", "Strawberries", "Eggs"),2),
                Quiz("Which character has Thor never spoken to on screen?", listOf("Peter Parker", "Vision", "Doctor Strange", "Rocket Raccoon"),0),
                Quiz("Which metal poisoned Tony Stark in Iron Man 2?", listOf("Vibranium", "Adamantium", "Palladium", "Vanadium"),2)
            )
        )
    )

    override fun getTopicByName(name: String): Topic? {
        return topics.find { it.title == name }
    }
    override fun getAllTopics(): List<Topic> {
        return topics
    }

    override fun addTopic(topic: Topic) {
        topics.add(topic)
    }
}
