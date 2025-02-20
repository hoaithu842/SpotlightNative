package io.github.hoaithu842.spotlight_native.manager

import androidx.media3.session.MediaSession
import kotlinx.coroutines.flow.Flow

interface MediaPlayerController {
    val mediaSession: MediaSession
    val isPlayingFlow: Flow<Boolean>
    val currentPositionFlow: Flow<Long>
    fun prepare(pathSource: String, listener: MediaPlayerListener)
    fun start()
    fun pause()
    fun seekTo(seconds: Long)
    fun getCurrentPosition(): Long
    fun getDuration(): Long
    fun stop()
    fun release()
    fun isPlaying(): Boolean
}