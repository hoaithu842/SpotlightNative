package io.github.hoaithu842.spotlight_native.manager

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotlightMediaPlayerController @Inject constructor(
    @ApplicationContext context: Context,
) : MediaPlayerController {
    private val player: ExoPlayer = ExoPlayer.Builder(context).build()

    override val mediaSession: MediaSession = MediaSession.Builder(context, player).build()

    override val isPlayingFlow: Flow<Boolean> = flow {
        while (true) {
            emit(player.isPlaying)
            delay(500)
        }
    }.flowOn(Dispatchers.Main)

    override val currentPositionFlow: Flow<Long> = flow {
        while (true) {
            if (player.isPlaying) {
                emit(player.currentPosition)
            }
            delay(500)
        }
    }.flowOn(Dispatchers.Main)


    override fun prepare(pathSource: String, listener: MediaPlayerListener) {
        val mediaItem = MediaItem.fromUri(pathSource)
        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                listener.onError()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    STATE_READY -> listener.onReady()
                    STATE_ENDED -> listener.onAudioCompleted()
                }
            }

            override fun onPlayerErrorChanged(error: PlaybackException?) {
                super.onPlayerErrorChanged(error)
                listener.onError()
            }
        })
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun start() {
        player.play()
    }

    override fun pause() {
        if (player.isPlaying)
            player.pause()
    }

    override fun seekTo(seconds: Long) {
        if (player.isPlaying)
            player.seekTo(seconds)
    }

    override fun getCurrentPosition(): Long {
        return player.currentPosition
    }

    override fun getDuration(): Long {
        return player.duration
    }

    override fun stop() {
        player.stop()
    }

    override fun release() {
        player.release()
    }

    override fun isPlaying(): Boolean {
        return player.isPlaying
    }
}