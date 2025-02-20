package io.github.hoaithu842.spotlight_native

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight_native.domain.model.Song
import io.github.hoaithu842.spotlight_native.manager.MediaPlayerController
import io.github.hoaithu842.spotlight_native.manager.MediaPlayerListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mediaPlayerController: MediaPlayerController,
) : ViewModel(), MediaPlayerController by mediaPlayerController {
    var resourcePrepared = false
    val isPlaying = mediaPlayerController.isPlayingFlow
    val currentPosition = mediaPlayerController.currentPositionFlow

    private val _currentSong = MutableStateFlow(playlist[currentPlayingIndex])
    val currentSong: StateFlow<Song> = _currentSong.asStateFlow()

    override fun next() {
        if (currentPlayingIndex + 1 < playlist.size) {
            currentPlayingIndex++
            _currentSong.value = playlist[currentPlayingIndex] // Update current song
            prepare(object : MediaPlayerListener {
                override fun onReady() {}
                override fun onAudioCompleted() {}
                override fun onError() {}
            })
        }
    }

    override fun prev() {
        if (currentPlayingIndex - 1 >= 0) {
            currentPlayingIndex--
            _currentSong.value = playlist[currentPlayingIndex] // Update current song
            prepare(object : MediaPlayerListener {
                override fun onReady() {}
                override fun onAudioCompleted() {}
                override fun onError() {}
            })
        }
    }


    fun process() {
        if (resourcePrepared) {
            if (isPlaying()) {
                pause()
            } else {
                start()
            }
        } else {
            prepare(
                listener = object : MediaPlayerListener {
                    override fun onReady() {
                        resourcePrepared = true
                    }

                    override fun onAudioCompleted() {
                        resourcePrepared = false
                        next()
                    }

                    override fun onError() {
                        resourcePrepared = false
                    }
                }
            )
        }
    }
//    fun process() {
//        if (resourcePrepared) {
//            if (isPlaying()) {
//                pause()
//            } else {
//                start()
//            }
//        } else {
//            prepare(
//                listener = object : MediaPlayerListener {
//                    override fun onReady() {
//                        resourcePrepared = true
//                    }
//
//                    override fun onAudioCompleted() {
//                        resourcePrepared = false
//                        next()
//                    }
//
//                    override fun onError() {
//                        resourcePrepared = false
//                    }
//                }
//            )
//        }
//    }
}