package io.github.hoaithu842.spotlight_native.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "HomeScreen",
            modifier = Modifier.align(Alignment.Center),
        )
    }
}