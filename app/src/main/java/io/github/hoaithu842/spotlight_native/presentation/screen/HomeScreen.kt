package io.github.hoaithu842.spotlight_native.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.presentation.component.HorizontalCircularThumbnail
import io.github.hoaithu842.spotlight_native.presentation.component.HorizontalRoundedCornerThumbnail
import io.github.hoaithu842.spotlight_native.presentation.component.PlaylistBottomSheet
import io.github.hoaithu842.spotlight_native.presentation.component.VerticalRoundedCornerThumbnail
import io.github.hoaithu842.spotlight_native.presentation.component.VerticalRoundedCornerWithTitleThumbnail
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeScreenTab
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeTopAppBar
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightTextStyle

@Composable
fun HomeScreen(
    onAvatarClick: () -> Unit,
    onRecommendedPlaylistClick: (Int) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        var currentTab by remember { mutableStateOf(HomeScreenTab.All) }

        HomeTopAppBar(
            onAvatarClick = onAvatarClick,
            currentTab = currentTab,
            onTabClick = { tab ->
                if (tab != currentTab) {
                    currentTab = tab
                }
            },
            modifier = Modifier
                .statusBarsPadding()
                .height(SpotlightDimens.TopAppBarHeight)
        )

        when (currentTab) {
            HomeScreenTab.All -> AllTab(
                onRecommendedPlaylistClick = onRecommendedPlaylistClick,
                onLongPress = { showBottomSheet = true }
            )

            HomeScreenTab.Music -> Text(
                text = "Home - Music",
            )

            HomeScreenTab.Podcasts -> Text(
                text = "Home - Podcasts",
            )
        }
    }
    if (showBottomSheet) {
        PlaylistBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllTab(
    onRecommendedPlaylistClick: (Int) -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        // Recently Group
        FlowRow(
            maxItemsInEachRow = 2,
            modifier = Modifier.padding(SpotlightDimens.RecommendationPadding)
        ) {
            HorizontalCircularThumbnail(
                artist = "Justatee",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                onLongPress = {},
                modifier = Modifier
                    .padding(SpotlightDimens.RecommendationPadding)
                    .weight(1f),
            )
            HorizontalRoundedCornerThumbnail(
                artist = "Justatee",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                onLongPress = {},
                modifier = Modifier
                    .padding(SpotlightDimens.RecommendationPadding)
                    .weight(1f),
            )
            HorizontalCircularThumbnail(
                artist = "Justatee",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                onLongPress = {},
                modifier = Modifier
                    .padding(SpotlightDimens.RecommendationPadding)
                    .weight(1f),
            )
            HorizontalRoundedCornerThumbnail(
                artist = "Justatee",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                onLongPress = {},
                modifier = Modifier
                    .padding(SpotlightDimens.RecommendationPadding)
                    .weight(1f),
            )
            HorizontalCircularThumbnail(
                artist = "Justatee",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                onLongPress = {},
                modifier = Modifier
                    .padding(SpotlightDimens.RecommendationPadding)
                    .weight(1f),
            )
            HorizontalRoundedCornerThumbnail(
                artist = "Justatee",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                onLongPress = {},
                modifier = Modifier
                    .padding(SpotlightDimens.RecommendationPadding)
                    .weight(1f),
            )
        }

        RecommendationSection(
            title = "Today's biggest hits",
            onRecommendedPlaylistClick = onRecommendedPlaylistClick,
            onLongPress = onLongPress,
        )

        RecommendationSection(
            title = "Popular albums and singles",
            onRecommendedPlaylistClick = onRecommendedPlaylistClick,
            onLongPress = onLongPress,
        )

        RecentSection(
            title = "Recents",
            onRecommendedPlaylistClick = onRecommendedPlaylistClick,
        )

        RecommendationSection(
            title = "Charts",
            onRecommendedPlaylistClick = onRecommendedPlaylistClick,
            onLongPress = onLongPress,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(SpotlightDimens.MinimizedPlayerHeight)
        )
    }
}

@Composable
fun RecommendationSection(
    title: String,
    onRecommendedPlaylistClick: (Int) -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(SpotlightDimens.RecommendationSectionHeight)
    ) {
        Text(
            text = title,
            style = SpotlightTextStyle.Text22W700,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(6) {
                VerticalRoundedCornerThumbnail(
                    imageUrl = "https://thantrieu.com/resources/arts/1078245023.webp",
                    description = "The Weeknd, Lady Gaga, JENNIE, Charlie Puth, yung kai, Dhruv",
                    onClick = { onRecommendedPlaylistClick(it) },
                    onLongPress = onLongPress,
                )
            }
        }
    }
}

@Composable
fun RecentSection(
    title: String,
    onRecommendedPlaylistClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(SpotlightDimens.RecentSectionHeight)
    ) {
        Text(
            text = title,
            style = SpotlightTextStyle.Text22W700,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(6) {
                VerticalRoundedCornerWithTitleThumbnail(
                    imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                    title = "Yen",
                    description = "The Weeknd, Lady Gaga, JENNIE, Charlie Puth, yung kai, Dhruv",
                    onClick = { onRecommendedPlaylistClick(it) },
                )
            }
        }
    }
}