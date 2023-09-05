package com.harutyun.androidfirebasedemo.presentation.helpers

class TextHelper


/**
 * Extension function to format a string by capitalizing the first letter,
 * trimming spaces around it, and removing continuous spaces between words.
 *
 * @return The formatted string.
 */

fun String.format(): String {
    val words = this.trim().split(Regex("\\s+"))

    var result = ""

    for ((index, word) in words.withIndex()) {
        if (word.isNotEmpty()) {
            result += if (index == 0) {
                word[0].uppercaseChar() + word.substring(1)
            } else {
                word
            }
            result += " "
        }
    }

    return result.trim()
}






