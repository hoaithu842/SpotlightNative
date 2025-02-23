package io.github.hoaithu842.spotlight_native.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SearchTopAppBar
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightDimens

@Composable
fun SearchScreen(
    onAvatarClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        SearchTopAppBar(
            onAvatarClick = onAvatarClick,
            modifier = Modifier
                .statusBarsPadding()
                .height(SpotlightDimens.TopAppBarHeight)
        )
    }
}