package io.github.hoaithu842.spotlight_native.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeScreenTab
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeTopAppBar

@Composable
fun HomeScreen(
    onAvatarClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        var currentTab by remember { mutableStateOf(HomeScreenTab.All) }

        HomeTopAppBar(
            onAvatarClick = onAvatarClick,
            currentTab = currentTab,
            onTabClick = { tab ->
                if (tab != currentTab) {
                    currentTab = tab
                }
            }
        )

        when (currentTab) {
            HomeScreenTab.All -> Text(
                text = "Home - All",
            )

            HomeScreenTab.Music -> Text(
                text = "Home - Music",
            )

            HomeScreenTab.Podcasts -> Text(
                text = "Home - Podcasts",
            )
        }
    }
}