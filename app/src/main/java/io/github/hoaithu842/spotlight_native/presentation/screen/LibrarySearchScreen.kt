package io.github.hoaithu842.spotlight_native.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.hoaithu842.spotlight_native.ui.designsystem.LibrarySearchTopAppBar
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens

@Composable
fun LibrarySearchScreen(
    onCancelClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        var searchQuery by remember { mutableStateOf("") }
        LibrarySearchTopAppBar(
            searchQuery = searchQuery,
            onCancelClick = onCancelClick,
            onSearchQueryChanged = { searchQuery = it },
            modifier = Modifier
                .statusBarsPadding()
                .height(SpotlightDimens.TopAppBarHeight),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

        }
    }
}