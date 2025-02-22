package io.github.hoaithu842.spotlight_native.presentation.viewmodel

import androidx.lifecycle.ViewModel
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
    private var isInit = false
    private lateinit var mediaController: MediaController

    val isPlayingFlow: Flow<Boolean> = flow {
        while (true) {
            if (isInit) {
                emit(mediaController.isPlaying)
            }
            delay(500)
        }
    }.flowOn(Dispatchers.Main)

    val currentPositionFlow: Flow<Long> = flow {
        while (true) {
            if (isInit && mediaController.isPlaying) {
                emit(mediaController.currentPosition)
            }
            delay(500)
        }
    }.flowOn(Dispatchers.Main)

    val durationFlow: Flow<Long> = flow<Long> {
        while (true) {
            if (isInit) {
                emit(mediaController.duration)
            }
            delay(500)
        }
    }.flowOn(Dispatchers.Main)

    private val _currentSong = MutableStateFlow(Song())
    val currentSong: StateFlow<Song> = _currentSong.asStateFlow()

    fun next() {
        mediaController.seekToNextMediaItem()
        _currentSong.update { playerRepository.songsList[mediaController.currentMediaItemIndex] }
    }

    fun prev() {
        mediaController.seekToPreviousMediaItem()
        _currentSong.update { playerRepository.songsList[mediaController.currentMediaItemIndex] }
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
        isInit = true
        _currentSong.update { playerRepository.songsList[mediaController.currentMediaItemIndex] }
    }
}