package io.github.hoaithu842.spotlight.presentation.screen

import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ArtistCategory
import io.github.hoaithu842.spotlight.domain.model.Image
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.presentation.component.RoundedCornerCover
import io.github.hoaithu842.spotlight.presentation.component.SongItem
import io.github.hoaithu842.spotlight.presentation.component.VerticalCircularWithTitleThumbnail
import io.github.hoaithu842.spotlight.presentation.component.VerticalRoundedCornerThumbnail
import io.github.hoaithu842.spotlight.presentation.viewmodel.ArtistSongsUiState
import io.github.hoaithu842.spotlight.presentation.viewmodel.ArtistUiState
import io.github.hoaithu842.spotlight.presentation.viewmodel.ArtistViewModel
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    id: String,
    onBackClick: () -> Unit,
    viewModel: ArtistViewModel = hiltViewModel(),
) {
    val uiState by viewModel.artistUiState.collectAsState()
    val songsUiState by viewModel.artistSongsUiState.collectAsState()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val displayMetrics: DisplayMetrics = LocalContext.current.resources.displayMetrics
    val dpWidth =
        remember {
            displayMetrics.widthPixels / displayMetrics.density
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .background(MaterialTheme.colorScheme.surface),
    ) {
        when (uiState) {
            ArtistUiState.Error -> Text("Error")
            ArtistUiState.Loading -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                ) {
                    RoundedCornerCover(
                        imageUrl = "",
                        contentScale = ContentScale.FillWidth,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(dpWidth.dp * (1 - scrollBehavior.state.collapsedFraction)),
                    )
                    ArtistBanner(
                        artistName = "",
                        expandedHeight = dpWidth.dp,
                        scrollBehavior = scrollBehavior,
                        onBackClick = onBackClick,
                    )
                }
                LazyColumn {
                    items(100, key = { it }) {
                        SongItem(
                            song = Song(),
                            cover = Image(),
                            modifier = Modifier.padding(vertical = SpotlightDimens.TopAppBarHorizontalPadding),
                        )
                    }
                }
            }

            is ArtistUiState.Success -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                ) {
                    RoundedCornerCover(
                        imageUrl =
                            (uiState as ArtistUiState.Success).artistDetails.image?.url
                                ?: "",
                        contentScale = ContentScale.FillWidth,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(dpWidth.dp * (1 - scrollBehavior.state.collapsedFraction)),
                    )
                    ArtistBanner(
                        artistName = (uiState as ArtistUiState.Success).artistDetails.name,
                        expandedHeight = dpWidth.dp,
                        scrollBehavior = scrollBehavior,
                        onBackClick = onBackClick,
                    )
                }
                LazyColumn {
                    when (songsUiState) {
                        ArtistSongsUiState.Error -> {}
                        ArtistSongsUiState.Loading -> {}
                        is ArtistSongsUiState.Success -> {
                            item {
                                Text(
                                    "Popular",
                                    style = SpotlightTextStyle.Text16W600,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(10.dp),
                                )
                            }
                            items(
                                (songsUiState as ArtistSongsUiState.Success).songsList.items.size,
                            ) {
                                val item =
                                    (songsUiState as ArtistSongsUiState.Success).songsList.items[it]
                                SongItem(
                                    song = Song(name = item.title, url = item.url, id = item.id),
                                    cover = item.image,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                )
                            }
                        }
                    }

                    items(
                        (uiState as ArtistUiState.Success).artistDetails.categories.size,
                    ) {
                        ArtistSectionDisplay(
                            artistCategory = (uiState as ArtistUiState.Success).artistDetails.categories[it],
                            onArtistClick = {},
                            onRecommendedPlaylistClick = {},
                            onLongPress = {},
                        )
// SongItem(
// modifier = Modifier.padding(vertical = SpotlightDimens.TopAppBarHorizontalPadding),
// )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistBanner(
    artistName: String,
    expandedHeight: Dp,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit,
) {
    LargeTopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = artistName,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                )
            }
        },
        colors =
            TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurface,
            ),
        scrollBehavior = scrollBehavior,
        expandedHeight = expandedHeight,
        collapsedHeight = 0.3 * 100.dp,
    )
}

@Composable
fun ArtistSectionDisplay(
    artistCategory: ArtistCategory,
    onArtistClick: (String) -> Unit,
    onRecommendedPlaylistClick: (Int) -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.RecommendationSectionHeight),
    ) {
        Text(
            text = artistCategory.name,
            style = SpotlightTextStyle.Text22W700,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(10.dp),
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(artistCategory.items.size) { index ->
                if (artistCategory.items[index].type == "artist") {
                    val artist = artistCategory.items[index]
                    VerticalCircularWithTitleThumbnail(
                        imageUrl = artist.image.url ?: "",
                        title = artist.title,
                        description = artist.type,
                        onClick = { onArtistClick(artist.id) },
                    )
                } else {
                    VerticalRoundedCornerThumbnail(
                        imageUrl = artistCategory.items[index].image.url,
                        description = artistCategory.items[index].title,
                        onClick = { onRecommendedPlaylistClick(index) },
                        onLongPress = onLongPress,
                    )
                }
            }
        }
    }
}
