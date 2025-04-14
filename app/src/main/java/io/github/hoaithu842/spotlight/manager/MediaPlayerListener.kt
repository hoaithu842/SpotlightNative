package io.github.hoaithu842.spotlight.manager

interface MediaPlayerListener {
    fun onReady()

    fun onAudioCompleted()

    fun onError()
}
