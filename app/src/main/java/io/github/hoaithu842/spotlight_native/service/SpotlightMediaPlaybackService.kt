package io.github.hoaithu842.spotlight_native.service

import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import io.github.hoaithu842.spotlight_native.domain.repository.PlayerRepository
import javax.inject.Inject

@AndroidEntryPoint
class SpotlightMediaPlaybackService : MediaSessionService() {
    @Inject
    lateinit var playerRepository: PlayerRepository
    private var mediaSession: MediaSession? = null

    private fun fetchPlaylist(player: ExoPlayer) {
        player.clearMediaItems()
        playerRepository.songsList.forEach {
            player.addMediaItem(MediaItem.fromUri(it.source))
        }
        player.prepare()
    }

    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        fetchPlaylist(player)
        mediaSession = MediaSession.Builder(this, player).build()
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    @OptIn(UnstableApi::class)
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        pauseAllPlayersAndStopSelf()
    }
}