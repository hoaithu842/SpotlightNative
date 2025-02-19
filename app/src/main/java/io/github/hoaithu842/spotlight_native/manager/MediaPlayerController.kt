package io.github.hoaithu842.spotlight_native.manager

interface MediaPlayerController {
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