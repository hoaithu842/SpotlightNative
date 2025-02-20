package io.github.hoaithu842.spotlight_native

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight_native.manager.MediaPlayerController
import io.github.hoaithu842.spotlight_native.manager.MediaPlayerListener
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mediaPlayerController: MediaPlayerController,
) : ViewModel(), MediaPlayerController by mediaPlayerController {
    var resourcePrepared = false
    val isPlaying = mediaPlayerController.isPlayingFlow
    val currentPosition = mediaPlayerController.currentPositionFlow

    fun process() {
        if (resourcePrepared) {
            if (isPlaying()) {
                pause()
            } else {
                start()
            }
        } else {
            prepare(
                pathSource = "https://thantrieu.com/resources/music/1073419268.mp3",
                listener = object : MediaPlayerListener {
                    override fun onReady() {
                        resourcePrepared = true
                    }

                    override fun onAudioCompleted() {
                        resourcePrepared = false
                    }

                    override fun onError() {
                        resourcePrepared = false
                    }
                }
            )
        }
    }
}