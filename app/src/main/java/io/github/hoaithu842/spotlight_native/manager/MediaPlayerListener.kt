package io.github.hoaithu842.spotlight_native.manager

interface MediaPlayerListener {
    fun onReady()
    fun onAudioCompleted()
    fun onError()
}