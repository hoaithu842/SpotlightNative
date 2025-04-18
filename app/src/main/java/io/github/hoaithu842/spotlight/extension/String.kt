package io.github.hoaithu842.spotlight.extension

import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    val hex = this.removePrefix("#")
    val colorLong = hex.toLong(16)
    return when (hex.length) {
        6 -> Color(colorLong or 0x00000000FF000000) // Add full opacity
        8 -> Color(colorLong) // Already includes alpha
        else -> Color.White
    }
}
