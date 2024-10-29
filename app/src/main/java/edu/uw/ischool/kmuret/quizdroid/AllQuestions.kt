package edu.uw.ischool.kmuret.quizdroid

object AllQuestions {
    private val mathQuestions = listOf(
        SingleQuestion("What is the square root of 16?", listOf("2", "4", "8", "16"), 1),
        SingleQuestion("What is 12 * 12?", listOf("122", "132", "144", "156"), 2),
        SingleQuestion("What is the value of π (pi) rounded to 2 decimal places?", listOf("3.14", "2.71", "1.61", "3.33"), 0),
        SingleQuestion("What is the derivative of x^2?", listOf("x", "2x", "x^2", "2x^2"), 1),
        SingleQuestion("What is the integral of 1/x?", listOf("ln(x)", "1/x^2", "x^2", "x^3/3"), 0),
        SingleQuestion("What is 7 + (6 * 5^2 - 3)?", listOf("100", "103", "170", "148"), 1),
        SingleQuestion("If f(x) = 2x + 3, what is f(4)?", listOf("8", "11", "5", "10"), 3)
    )

    private val physicsQuestions = listOf(
        SingleQuestion("What is the speed of light in a vacuum?", listOf("3x10^5 m/s", "3x10^8 m/s", "3x10^3 m/s", "3x10^6 m/s"), 1),
        SingleQuestion("Who is known as the father of modern physics?", listOf("Isaac Newton", "Albert Einstein", "Galileo Galilei", "Niels Bohr"), 2),
        SingleQuestion("What type of particle is an electron?", listOf("Quark", "Neutron", "Lepton", "Boson"), 2),
        SingleQuestion("What is the unit of electrical resistance?", listOf("Joule", "Volt", "Ohm", "Watt"), 2),
        SingleQuestion("What is the first law of thermodynamics about?", listOf("Energy conservation", "Entropy", "Action and Reaction", "Gravity"), 0),
        SingleQuestion("What is the formula for calculating kinetic energy?", listOf("mv^2", "1/2 mv^2", "ma", "1/2 ma"), 1),
        SingleQuestion("Who discovered radioactivity?", listOf("Marie Curie", "Albert Einstein", "Isaac Newton", "Enrico Fermi"), 0)
    )

    private val marvelQuestions = listOf(
        SingleQuestion("Which metal is used to make Captain America's shield?", listOf("Adamantium", "Uru", "Vibranium", "Titanium"), 2),
        SingleQuestion("What is the real name of Black Panther?", listOf("T'Challa", "M'Baku", "Okoye", "Shuri"), 0),
        SingleQuestion("Which Avenger is a Norse god?", listOf("Iron Man", "Hawkeye", "Thor", "Hulk"), 2),
        SingleQuestion("What is Deadpool's real name?", listOf("Wade Wilson", "Peter Parker", "Matt Murdock", "Logan"), 0),
        SingleQuestion("Who is the villain in Avengers: Infinity War?", listOf("Thanos", "Loki", "Ultron", "Ronan"), 0),
        SingleQuestion("Which Avenger uses a bow and arrow?", listOf("Black Widow", "Hawkeye", "Captain America", "Iron Man"), 1),
        SingleQuestion("What is Scarlet Witch's real name?", listOf("Natasha Romanoff", "Wanda Maximoff", "Pepper Potts", "Carol Danvers"), 1)
    )

    val topics = mapOf(
        "Math" to Topic("Math questions.", mathQuestions),
        "Physics" to Topic("Physics questions.", physicsQuestions),
        "Marvel Super Heroes" to Topic("Marvel Super Heroes questions.", marvelQuestions)
    )
}
