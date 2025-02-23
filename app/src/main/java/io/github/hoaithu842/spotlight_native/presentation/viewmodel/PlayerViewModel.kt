package io.github.hoaithu842.spotlight_native.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.MediaController
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight_native.domain.model.Song
import io.github.hoaithu842.spotlight_native.domain.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
) : ViewModel() {
    private lateinit var mediaController: MediaController

    val currentPositionFlow: Flow<Long> = flow {
        while (true) {
            if (::mediaController.isInitialized && mediaController.isPlaying) {
                emit(mediaController.currentPosition)
            }
            delay(500)
        }
    }.flowOn(Dispatchers.Main)

    private val _playerUiState: MutableStateFlow<PlayerUiState> =
        MutableStateFlow(PlayerUiState(isPlaying = false, duration = 0, currentSong = Song()))
    val playerUiState: StateFlow<PlayerUiState> = _playerUiState.asStateFlow()

    fun next() {
        mediaController.seekToNextMediaItem()
    }

    fun prev() {
        mediaController.seekToPreviousMediaItem()
    }

    fun playOrPause() {
        if (mediaController.isPlaying) {
            mediaController.pause()
        } else {
            mediaController.play()
        }
    }

    fun setController(mediaController: MediaController) {
        this.mediaController = mediaController
        mediaController.addListener(object : Player.Listener {
            override fun onTracksChanged(tracks: Tracks) {
                super.onTracksChanged(tracks)
                _playerUiState.update {
                    it.copy(
                        currentSong = playerRepository.songsList[mediaController.currentMediaItemIndex],
                        duration = mediaController.duration,
                    )
                }
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                _playerUiState.update {
                    it.copy(
                        isPlaying = isPlaying,
                    )
                }
            }
        })
    }
}