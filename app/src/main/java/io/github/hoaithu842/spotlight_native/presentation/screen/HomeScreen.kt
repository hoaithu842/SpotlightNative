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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.hoaithu842.spotlight_native.domain.model.HomeSection
import io.github.hoaithu842.spotlight_native.presentation.component.HorizontalCircularThumbnail
import io.github.hoaithu842.spotlight_native.presentation.component.HorizontalRoundedCornerThumbnail
import io.github.hoaithu842.spotlight_native.presentation.component.PlaylistBottomSheet
import io.github.hoaithu842.spotlight_native.presentation.component.VerticalCircularWithTitleThumbnail
import io.github.hoaithu842.spotlight_native.presentation.component.VerticalRoundedCornerThumbnail
import io.github.hoaithu842.spotlight_native.presentation.viewmodel.HomeUiState
import io.github.hoaithu842.spotlight_native.presentation.viewmodel.HomeViewModel
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeScreenTab
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeTopAppBar
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightTextStyle

@Composable
fun HomeScreen(
    onAvatarClick: () -> Unit,
    onArtistClick: (String) -> Unit,
    onRecommendedPlaylistClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.homeUiState.collectAsState()

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
                uiState = uiState,
                onArtistClick = onArtistClick,
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
    uiState: HomeUiState,
    onArtistClick: (String) -> Unit,
    onRecommendedPlaylistClick: (Int) -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is HomeUiState.Error -> {
                item {
                    Text(text = "Error")
                }
            }

            is HomeUiState.Loading -> {
                item {
                    Text(text = "Loading")
                }
            }

            is HomeUiState.Success -> {
                if (uiState.contents.isNotEmpty()) {
                    item {
                        FlowRow(
                            maxItemsInEachRow = 2,
                            modifier = Modifier.padding(SpotlightDimens.RecommendationPadding)
                        ) {
                            uiState.contents.first().items.forEach {
                                if (it.type == "artist") {
                                    HorizontalCircularThumbnail(
                                        artist = it.name,
                                        imageUrl = it.image?.url ?: "",
                                        onLongPress = {},
                                        modifier = Modifier
                                            .padding(SpotlightDimens.RecommendationPadding)
                                            .weight(1f),
                                    )
                                } else {
                                    HorizontalRoundedCornerThumbnail(
                                        artist = it.name,
                                        imageUrl = it.image?.url ?: "",
                                        onLongPress = {},
                                        modifier = Modifier
                                            .padding(SpotlightDimens.RecommendationPadding)
                                            .weight(1f),
                                    )
                                }
                            }
                        }
                    }
                    items(uiState.contents.size) { index ->
                        if (index != 0) {
                            HomeSectionDisplay(
                                uiState.contents[index],
                                onArtistClick = onArtistClick,
                                onRecommendedPlaylistClick = onRecommendedPlaylistClick,
                                onLongPress = {},
                            )
                        }
                    }
                }
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SpotlightDimens.MinimizedPlayerHeight)
            )
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(SpotlightDimens.MinimizedPlayerHeight)
    )
}

@Composable
fun HomeSectionDisplay(
    homeSection: HomeSection,
    onArtistClick: (String) -> Unit,
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
            text = homeSection.name,
            style = SpotlightTextStyle.Text22W700,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(10.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(homeSection.items.size) { index ->
                if (homeSection.items[index].type == "artist") {
                    val artist = homeSection.items[index]
                    VerticalCircularWithTitleThumbnail(
                        imageUrl = artist.image?.url ?: "",
                        title = artist.name,
                        description = artist.type,
                        onClick = { onArtistClick(artist.id) },
                    )
                } else {
                    VerticalRoundedCornerThumbnail(
                        imageUrl = homeSection.items[index].image?.url ?: "",
                        description = homeSection.items[index].artists?.joinToString { it.name }
                            ?: "",
                        onClick = { onRecommendedPlaylistClick(index) },
                        onLongPress = onLongPress,
                    )
                }
            }
        }
    }
}