package io.github.hoaithu842.spotlight_native.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.github.hoaithu842.spotlight_native.presentation.component.FullsizePlayer
import io.github.hoaithu842.spotlight_native.presentation.component.MinimizedPlayer
import io.github.hoaithu842.spotlight_native.presentation.viewmodel.PlayerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerView(
    scaffoldState: BottomSheetScaffoldState,
    navBarDisplayingChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    val currentPosition by viewModel.currentPositionFlow.collectAsStateWithLifecycle(initialValue = 0)
    val playerUiState by viewModel.playerUiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    var isLoading by remember { mutableStateOf(true) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(playerUiState.currentSong.image)
            .listener(onSuccess = { _, _ ->
                isLoading = false
            })
            .build()
    )

    AnimatedContent(
        targetState = scaffoldState.bottomSheetState.currentValue,
        label = "",
    ) {
        when (it) {
            SheetValue.Hidden -> {}
            SheetValue.Expanded -> {
                FullsizePlayer(
                    isPlaying = playerUiState.isPlaying,
                    song = playerUiState.currentSong,
                    currentPosition = currentPosition,
                    duration = playerUiState.duration,
                    isLoading = isLoading,
                    painter = painter,
                    onMinimizeClick = {
                        coroutineScope.launch {
                            navBarDisplayingChange(true)
                            scaffoldState.bottomSheetState.partialExpand()
                        }
                    },
                    onPrevClick = viewModel::prev,
                    onMainFunctionClick = viewModel::playOrPause,
                    onNextClick = viewModel::next,
                )
            }

            SheetValue.PartiallyExpanded -> {
                MinimizedPlayer(
                    isPlaying = playerUiState.isPlaying,
                    song = playerUiState.currentSong,
                    currentPosition = currentPosition,
                    duration = playerUiState.duration,
                    isLoading = isLoading,
                    painter = painter,
                    onPlayerClick = {
                        coroutineScope.launch {
                            navBarDisplayingChange(false)
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    onMainFunctionClick = viewModel::playOrPause,
                )
            }
        }
    }
}