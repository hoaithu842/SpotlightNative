package io.github.hoaithu842.spotlight.presentation.screen

import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.hoaithu842.spotlight.domain.model.Image
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.extension.noRippleClickable
import io.github.hoaithu842.spotlight.presentation.component.SongItem
import io.github.hoaithu842.spotlight.presentation.viewmodel.LibraryFavoriteUiState
import io.github.hoaithu842.spotlight.presentation.viewmodel.LibraryFavoriteViewModel
import io.github.hoaithu842.spotlight.ui.designsystem.DotsCollision
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight.ui.theme.MinimizedPlayerBackground
import io.github.hoaithu842.spotlight.ui.theme.NavigationGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryFavoriteScreen(
    onCancelClick: () -> Unit,
    viewModel: LibraryFavoriteViewModel = hiltViewModel(),
) {
    val uiState by viewModel.libraryFavoriteUiState.collectAsStateWithLifecycle()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val displayMetrics: DisplayMetrics = LocalContext.current.resources.displayMetrics
    val dpWidth = 200.dp

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .background(MaterialTheme.colorScheme.surface),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
        ) {
            HeadlineCover(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(dpWidth * (1 - scrollBehavior.state.collapsedFraction) * 0.9f),
                collapsedFraction = scrollBehavior.state.collapsedFraction,
            )

            Headline(
                artistName = "Liked Songs",
                expandedHeight = dpWidth,
                scrollBehavior = scrollBehavior,
                onBackClick = onCancelClick,
            )
        }

        when (uiState) {
            LibraryFavoriteUiState.Error -> {
                MainFunctionBar(
                    onPlayClick = {},
                    onPauseClick = {},
                    modifier =
                        Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 15.dp,
                        ),
                )
            }

            LibraryFavoriteUiState.Loading -> {
                MainFunctionBar(
                    onPlayClick = {},
                    onPauseClick = {},
                    modifier =
                        Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 15.dp,
                        ),
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    DotsCollision()
                }
            }

            is LibraryFavoriteUiState.Success -> {
                MainFunctionBar(
                    onPlayClick = { viewModel.playAlbum((uiState as LibraryFavoriteUiState.Success).items) },
                    onPauseClick = { viewModel.pause() },
                    modifier =
                        Modifier.padding(
                            horizontal = 20.dp,
                            vertical = 15.dp,
                        ),
                )
                LazyColumn(
                    contentPadding = PaddingValues(bottom = SpotlightDimens.MinimizedPlayerHeight),
                ) {
                    items(
                        (uiState as LibraryFavoriteUiState.Success).items.size,
                    ) { index ->
                        val item =
                            (uiState as LibraryFavoriteUiState.Success).items[index]
                        SongItem(
                            song =
                                Song(
                                    name = item.title ?: "",
                                    url = item.url ?: "",
                                    id = item.id ?: "",
                                ),
                            cover = item.image ?: Image(),
                            artists =
                                item.artists?.joinToString(separator = ", ") { it.name }
                                    ?: "",
                            modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeadlineCover(
    modifier: Modifier = Modifier,
    collapsedFraction: Float,
) {
    val gradientBrush =
        Brush.verticalGradient(
            colors =
                listOf(
                    MinimizedPlayerBackground.copy(alpha = 1f - collapsedFraction),
                    MaterialTheme.colorScheme.surface.copy(alpha = collapsedFraction),
                ),
        )

    Spacer(
        modifier =
            modifier
                .background(
                    brush = gradientBrush,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Headline(
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
fun MainFunctionBar(
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isPlaying by remember { mutableStateOf(false) }
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.FullsizePlayerTopAppBarHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(SpotlightIcons.Download),
                contentDescription = "",
                tint = NavigationGray,
                modifier = Modifier.size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            )
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "",
                tint = NavigationGray,
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.RecommendationPadding * 3)
                        .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(SpotlightIcons.Shuffle),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.RecommendationPadding * 3)
                        .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            )
            Icon(
                painter = painterResource(if (!isPlaying) SpotlightIcons.Play else SpotlightIcons.Pause),
                contentDescription = "",
                tint = MinimizedPlayerBackground,
                modifier =
                    Modifier
                        .size(SpotlightDimens.FullsizePlayerTopAppBarHeight)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding((SpotlightDimens.FullsizePlayerTopAppBarHeight - SpotlightDimens.PlayerControllerMediumIconSize) / 2)
                        .noRippleClickable {
                            isPlaying = !isPlaying
                            if (isPlaying) onPlayClick() else onPauseClick()
                        },
            )
        }
    }
}
