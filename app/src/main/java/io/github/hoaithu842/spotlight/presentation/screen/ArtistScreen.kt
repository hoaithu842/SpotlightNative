package io.github.hoaithu842.spotlight.presentation.screen

import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ArtistCategory
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.extension.noRippleClickable
import io.github.hoaithu842.spotlight.presentation.component.RoundedCornerCover
import io.github.hoaithu842.spotlight.presentation.component.SongItem
import io.github.hoaithu842.spotlight.presentation.component.VerticalCircularWithTitleThumbnail
import io.github.hoaithu842.spotlight.presentation.component.VerticalRoundedCornerThumbnail
import io.github.hoaithu842.spotlight.presentation.viewmodel.ArtistSongsUiState
import io.github.hoaithu842.spotlight.presentation.viewmodel.ArtistUiState
import io.github.hoaithu842.spotlight.presentation.viewmodel.ArtistViewModel
import io.github.hoaithu842.spotlight.ui.designsystem.DotsCollision
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight.ui.theme.MinimizedPlayerBackground

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
            ArtistUiState.Error -> {
            }

            ArtistUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    DotsCollision()
                }
            }

            is ArtistUiState.Success -> {
                var isPlaying by remember { mutableStateOf(false) }

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
                                .height(dpWidth.dp * (1 - scrollBehavior.state.collapsedFraction) * 0.9f),
                    )
                    ArtistBanner(
                        artistName = (uiState as ArtistUiState.Success).artistDetails.name,
                        expandedHeight = dpWidth.dp,
                        scrollBehavior = scrollBehavior,
                        onBackClick = onBackClick,
                    )
                }
                LazyColumn(
                    contentPadding = PaddingValues(bottom = SpotlightDimens.MinimizedPlayerHeight),
                ) {
                    when (songsUiState) {
                        ArtistSongsUiState.Error -> {}
                        ArtistSongsUiState.Loading -> {
                            item {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    DotsCollision()
                                }
                            }
                        }

                        is ArtistSongsUiState.Success -> {
                            if ((songsUiState as ArtistSongsUiState.Success).songsList.items.isNotEmpty()) {
                                item {
                                    Row(
                                        modifier =
                                            Modifier
                                                .wrapContentHeight()
                                                .fillParentMaxWidth()
                                                .padding(horizontal = 20.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            "Popular",
                                            style = SpotlightTextStyle.Text16W600,
                                            color = MaterialTheme.colorScheme.onBackground,
                                        )
                                        Icon(
                                            painter =
                                                painterResource(
                                                    id =
                                                        if (isPlaying) {
                                                            SpotlightIcons.Pause
                                                        } else {
                                                            SpotlightIcons.Play
                                                        },
                                                ),
                                            contentDescription = null,
                                            tint = MinimizedPlayerBackground,
                                            modifier =
                                                Modifier
                                                    .padding(bottom = 16.dp)
                                                    .size(SpotlightDimens.FullsizePlayerTopAppBarHeight * 0.75f)
                                                    .clip(CircleShape)
                                                    .background(MaterialTheme.colorScheme.primary)
                                                    .padding(
                                                        (
                                                            SpotlightDimens.FullsizePlayerTopAppBarHeight -
                                                                SpotlightDimens.PlayerControllerMediumIconSize
                                                        ) / 2,
                                                    )
                                                    .noRippleClickable {
                                                        if (isPlaying) {
                                                            viewModel.pause()
                                                        } else {
                                                            viewModel.playAlbum(
                                                                (songsUiState as ArtistSongsUiState.Success).songsList.items,
                                                            )
                                                        }
                                                        isPlaying = !isPlaying
                                                    },
                                        )
                                    }
                                }
                            }
                            items(
                                (songsUiState as ArtistSongsUiState.Success).songsList.items.size,
                            ) {
                                val item =
                                    (songsUiState as ArtistSongsUiState.Success).songsList.items[it]
                                SongItem(
                                    song = Song(name = item.title, url = item.url, id = item.id),
                                    cover = item.image,
                                    modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
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
                        description = artist.type.replaceFirstChar { it.uppercase() },
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
