package io.github.hoaithu842.spotlight_native

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight_native.manager.MediaPlayerController
import io.github.hoaithu842.spotlight_native.manager.MediaPlayerListener
import io.github.hoaithu842.spotlight_native.presentation.viewmodel.MediaPlayerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mediaPlayerController: MediaPlayerController,
) : ViewModel(), MediaPlayerController by mediaPlayerController {
    private val _playerState = MutableStateFlow(MediaPlayerUiState())
    val playerState = _playerState.asStateFlow()

    fun process() {
        if (_playerState.value.isPlaying) {
            pausePlayer()
        } else {
            startPlayer()
        }
    }

    private fun pausePlayer() {
        pause()
        _playerState.update { it.copy(isPlaying = false) }
    }

    private fun startPlayer() {
        prepare(
            pathSource = "https://thantrieu.com/resources/music/1073419268.mp3",
            listener = object : MediaPlayerListener {
                override fun onReady() {
                    _playerState.update { it.copy(isPlaying = true) }
                }

                override fun onAudioCompleted() {
                    _playerState.update { it.copy(isPlaying = false) }
                }

                override fun onError() {
                    _playerState.update { it.copy(isPlaying = false) }
                }
            }
        )
    }
}