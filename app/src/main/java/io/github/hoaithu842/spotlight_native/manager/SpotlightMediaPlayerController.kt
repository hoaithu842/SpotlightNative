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
import io.github.hoaithu842.spotlight_native.domain.model.Song
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

    override var currentPlayingIndex: Int = 0

    override var playlist: List<Song> = listOf(
        Song(
            title = "Beautiful In White",
            artists = "Shayne Ward",
            source = "https://thantrieu.com/resources/music/1073419268.mp3",
            image = "https://thantrieu.com/resources/arts/1073419268.webp",
        ),
        Song(
            title = "Giả Vờ Nhưng Em Yêu Anh",
            artists = "Miu Lê",
            source = "https://thantrieu.com/resources/music/1074592745.mp3",
            image = "https://thantrieu.com/resources/arts/1074183664.webp",
        ),
        Song(
            title = "Thằng Điên",
            artists = "JustaTee ft Phương Ly",
            source = "https://thantrieu.com/resources/music/1078245010.mp3",
            image = "https://thantrieu.com/resources/arts/1078245010.webp",
        ),
        Song(
            title = "Chạm Khẽ Tim Anh Một Chút Thôi",
            artists = "Noo Phước Thịnh",
            source = "https://thantrieu.com/resources/music/1078245023.mp3",
            image = "https://thantrieu.com/resources/arts/1078245023.webp",
        ),
        Song(
            title = "Người Ấy",
            artists = "Trịnh Thăng Bình",
            source = "https://thantrieu.com/resources/music/1078245024.mp3",
            image = "https://thantrieu.com/resources/arts/1078245024.webp",
        ),
    )

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


//    override fun prepare(listener: MediaPlayerListener) {
//        val mediaItem = MediaItem.fromUri(playlist[currentPlayingIndex].source)
//        player.addListener(object : Player.Listener {
//            override fun onPlayerError(error: PlaybackException) {
//                super.onPlayerError(error)
//                listener.onError()
//            }
//
//            override fun onPlaybackStateChanged(playbackState: Int) {
//                super.onPlaybackStateChanged(playbackState)
//                when (playbackState) {
//                    STATE_READY -> listener.onReady()
//                    STATE_ENDED -> listener.onAudioCompleted()
//                }
//            }
//
//            override fun onPlayerErrorChanged(error: PlaybackException?) {
//                super.onPlayerErrorChanged(error)
//                listener.onError()
//            }
//        })
//        player.setMediaItem(mediaItem)
//        player.prepare()
//        player.play()
//    }

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

//    override fun next() {
//        if (currentPlayingIndex + 1 < playlist.size) {
//            currentPlayingIndex++
//        }
//    }
//
//    override fun prev() {
//        if (currentPlayingIndex - 1 >= 0) {
//            currentPlayingIndex--
//        }
//    }
override fun prepare(listener: MediaPlayerListener) {
    playlist = listOf(
        Song(
            title = "Beautiful In White",
            artists = "Shayne Ward",
            source = "https://thantrieu.com/resources/music/1073419268.mp3",
            image = "https://thantrieu.com/resources/arts/1073419268.webp",
        ),
        Song(
            title = "Giả Vờ Nhưng Em Yêu Anh",
            artists = "Miu Lê",
            source = "https://thantrieu.com/resources/music/1074592745.mp3",
            image = "https://thantrieu.com/resources/arts/1074183664.webp",
        ),
        Song(
            title = "Thằng Điên",
            artists = "JustaTee ft Phương Ly",
            source = "https://thantrieu.com/resources/music/1078245010.mp3",
            image = "https://thantrieu.com/resources/arts/1078245010.webp",
        ),
        Song(
            title = "Chạm Khẽ Tim Anh Một Chút Thôi",
            artists = "Noo Phước Thịnh",
            source = "https://thantrieu.com/resources/music/1078245023.mp3",
            image = "https://thantrieu.com/resources/arts/1078245023.webp",
        ),
        Song(
            title = "Người Ấy",
            artists = "Trịnh Thăng Bình",
            source = "https://thantrieu.com/resources/music/1078245024.mp3",
            image = "https://thantrieu.com/resources/arts/1078245024.webp",
        ),
    )
            //mediaRepository.getSongs() // Fetch songs dynamically
    if (playlist.isEmpty()) return

    loadSong(listener)
}

    private fun loadSong(listener: MediaPlayerListener) {
        val mediaItem = MediaItem.fromUri(playlist[currentPlayingIndex].source)
        player.clearMediaItems()
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    STATE_READY -> listener.onReady()
                    STATE_ENDED -> listener.onAudioCompleted()
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                listener.onError()
            }
        })
    }

    override fun next() {
        if (currentPlayingIndex + 1 < playlist.size) {
            currentPlayingIndex++
            loadSong(object : MediaPlayerListener {
                override fun onReady() {}
                override fun onAudioCompleted() {}
                override fun onError() {}
            })
        }
    }

    override fun prev() {
        if (currentPlayingIndex - 1 >= 0) {
            currentPlayingIndex--
            loadSong(object : MediaPlayerListener {
                override fun onReady() {}
                override fun onAudioCompleted() {}
                override fun onError() {}
            })
        }
    }
}