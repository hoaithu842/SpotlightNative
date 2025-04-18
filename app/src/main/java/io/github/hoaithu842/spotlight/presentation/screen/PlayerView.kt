package io.github.hoaithu842.spotlight.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.github.hoaithu842.spotlight.extension.gesturesDisabled
import io.github.hoaithu842.spotlight.presentation.component.ArtistCard
import io.github.hoaithu842.spotlight.presentation.component.CreditCard
import io.github.hoaithu842.spotlight.presentation.component.LyricsCard
import io.github.hoaithu842.spotlight.presentation.component.MainPlayerContent
import io.github.hoaithu842.spotlight.presentation.component.MinimizedPlayer
import io.github.hoaithu842.spotlight.presentation.viewmodel.PlayerViewModel
import io.github.hoaithu842.spotlight.ui.designsystem.FullsizePlayerTopAppBar
import io.github.hoaithu842.spotlight.ui.designsystem.PlayerControllerTopAppBar
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.theme.MinimizedPlayerBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerView(
    scaffoldState: BottomSheetScaffoldState,
    onScrollProvider: () -> Float,
    updateDelta: (Float) -> Unit,
    changeNavBarDisplay: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    val currentPosition by viewModel.currentPositionFlow.collectAsStateWithLifecycle(initialValue = 0)
    val currentSongArtist by viewModel.currentMediaMetadata.collectAsStateWithLifecycle(initialValue = null)
    val currentSong by viewModel.currentSongFlow.collectAsStateWithLifecycle(initialValue = null)
    val isPlaying by viewModel.isPlayingFlow.collectAsStateWithLifecycle(initialValue = false)
    val duration by viewModel.durationFlow.collectAsStateWithLifecycle(initialValue = 0)
    val coroutineScope = rememberCoroutineScope()

    var isLoading by remember { mutableStateOf(true) }
    val painter =
        rememberAsyncImagePainter(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .data(currentSong?.image?.url ?: "")
                    .listener(onSuccess = { _, _ ->
                        isLoading = false
                    })
                    .build(),
        )
    val lazyListState = rememberLazyListState()
    val shouldDisplayTopAppBar by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 1
        }
    }
    val scope = rememberCoroutineScope()
    var currentAlpha by remember { mutableFloatStateOf(1f) }
    var hasInit by remember { mutableStateOf(false) }
    var initialOffset by remember { mutableFloatStateOf(0f) }
    val shouldBeClickable by remember {
        derivedStateOf {
            currentAlpha == 1f
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(if (currentAlpha != 1f) MinimizedPlayerBackground else Color.Transparent),
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = SpotlightDimens.FullsizePlayerMainContentHorizontalPadding),
            state = lazyListState,
        ) {
            item {
                FullsizePlayerTopAppBar(
                    artists = currentSongArtist?.artist.toString(),
                    onMinimizeClick = {
                        coroutineScope.launch {
                            changeNavBarDisplay()
                            scaffoldState.bottomSheetState.partialExpand()
                        }
                    },
                    modifier = Modifier.statusBarsPadding(),
                )
            }
            item {
                MainPlayerContent(
                    isPlaying = isPlaying,
                    song = currentSong,
                    artists = currentSongArtist?.artist.toString(),
                    currentPosition = currentPosition,
                    duration = duration,
                    painter = painter,
                    isLoading = isLoading,
                    onPrevClick = viewModel::prev,
                    onMainFunctionClick = viewModel::playOrPause,
                    onNextClick = viewModel::next,
                )
            }

            item {
                LyricsCard(
                    modifier = Modifier.padding(vertical = 10.dp),
                )
            }

            item {
                ArtistCard(
                    imageUrl = currentSong?.image?.url ?: "",
                    modifier = Modifier.padding(vertical = 10.dp),
                )
            }

            item {
                CreditCard(
                    creditsList =
                        listOf(
                            Pair(first = "Jax", second = "Main Artist, Writer"),
                            Pair(first = "Billy Gerrity", second = "Producer"),
                            Pair(first = "Wayne Wilkins", second = "Producer, Writer"),
                        ),
                    modifier = Modifier.padding(vertical = 10.dp),
                )
            }
        }

        AnimatedVisibility(
            visible = shouldDisplayTopAppBar,
            enter = slideInVertically(),
            exit = fadeOut(),
        ) {
            PlayerControllerTopAppBar(
                song = currentSong,
                artists = currentSongArtist?.artist.toString(),
                isPlaying = isPlaying,
                currentPosition = currentPosition,
                duration = duration,
                onMainFunctionClick = viewModel::playOrPause,
                onPlayerClick = {
                    scope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.statusBarsPadding(),
            )
        }

        MinimizedPlayer(
            song = currentSong,
            artists = currentSongArtist?.artist.toString(),
            isPlaying = isPlaying,
            currentPosition = currentPosition,
            duration = duration,
            painter = painter,
            isLoading = isLoading,
            onPlayerClick = {
                coroutineScope.launch {
                    changeNavBarDisplay()
                    scaffoldState.bottomSheetState.expand()
                }
            },
            onMainFunctionClick = viewModel::playOrPause,
            modifier =
                Modifier
                    .align(Alignment.TopCenter)
                    .graphicsLayer {
                        val scrollOffset = onScrollProvider()

                        if (!hasInit) {
                            hasInit = true
                            initialOffset = scrollOffset
                        }

                        val sizeDelta =
                            1 - (initialOffset - scrollOffset).dp / SpotlightDimens.MinimizedPlayerHeight

                        updateDelta(sizeDelta)
                        currentAlpha = sizeDelta.coerceIn(0f, 1f)
                    }
                    .alpha(currentAlpha)
                    .gesturesDisabled(disabled = !shouldBeClickable),
        )
    }
}
