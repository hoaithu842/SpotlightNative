package io.github.hoaithu842.spotlight_native.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.hoaithu842.spotlight_native.presentation.component.FullsizePlayer
import io.github.hoaithu842.spotlight_native.presentation.component.MinimizedPlayer
import io.github.hoaithu842.spotlight_native.presentation.viewmodel.PlayerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerView(
    scaffoldState: BottomSheetScaffoldState,
    navBarDisplayingChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    val isPlaying by viewModel.isPlayingFlow.collectAsStateWithLifecycle(initialValue = false)
    val currentPosition by viewModel.currentPositionFlow.collectAsStateWithLifecycle(initialValue = 0)
    val duration by viewModel.durationFlow.collectAsStateWithLifecycle(initialValue = 0)
    val currentSong by viewModel.currentSong.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    AnimatedContent(
        targetState = scaffoldState.bottomSheetState.currentValue,
        label = "",
    ) {
        when (it) {
            SheetValue.Hidden -> {}
            SheetValue.Expanded -> {
                FullsizePlayer(
                    isPlaying = isPlaying,
                    song = currentSong,
                    currentPosition = currentPosition,
                    duration = duration,
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
                    isPlaying = isPlaying,
                    song = currentSong,
                    currentPosition = currentPosition,
                    duration = duration,
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