package io.github.hoaithu842.spotlight.service

import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import io.github.hoaithu842.spotlight.domain.repository.SongRepository
import javax.inject.Inject

@AndroidEntryPoint
class SpotlightMediaPlaybackService : MediaSessionService() {
    @Inject
    lateinit var playerRepository: SongRepository
    private var mediaSession: MediaSession? = null

//    private fun fetchPlaylist(player: ExoPlayer) {
//        player.clearMediaItems()
//        playerRepository.songsList.forEach {
//            player.addMediaItem(MediaItem.fromUri(it.song?.url ?: ""))
//        }
//        player.prepare()
//    }

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        val player =
            ExoPlayer.Builder(this)
                .setMediaSourceFactory(
                    ProgressiveMediaSource.Factory(
                        DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true),
                    ),
                ).build()
//        fetchPlaylist(player)
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
