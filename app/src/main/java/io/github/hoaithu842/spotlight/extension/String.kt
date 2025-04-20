package io.github.hoaithu842.spotlight.extension

import androidx.compose.ui.graphics.Color

fun String.toColor(): Color? {
    val hex = this.removePrefix("#")
    return try {
        val colorLong = hex.toLong(16)
        when (hex.length) {
            6 -> Color(colorLong or 0x00000000FF000000)
            8 -> Color(colorLong)
            else -> null
        }
    } catch (e: NumberFormatException) {
        null
    }
}
