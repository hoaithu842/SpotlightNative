package io.github.hoaithu842.spotlight_native.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.extensions.noRippleClickable
import io.github.hoaithu842.spotlight_native.ui.components.HomeScreenDrawer
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeScreenTab
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeTopAppBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = {
            2
        },
    )

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 20.dp),
        userScrollEnabled = false,
    ) { page ->
        if (page == 0) {
            HomeScreenDrawer()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable {
                        if (pagerState.currentPage == 0) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        }
                    },
            ) {
                var currentTab by remember { mutableStateOf(HomeScreenTab.All) }

                HomeTopAppBar(
                    onAvatarClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
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
    }
}