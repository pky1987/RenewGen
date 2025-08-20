package com.example.re

object BadWord {
    val badWordsList = listOf(
        "badword1", "badword2", "offensiveWord", "curseword"
    )

    fun containsBadWords(text: String): Boolean {
        return badWordsList.any { text.contains(it, ignoreCase = true) }
    }
}
