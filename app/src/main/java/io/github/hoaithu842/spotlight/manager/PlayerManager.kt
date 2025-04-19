package io.github.hoaithu842.spotlight.manager

import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import io.github.hoaithu842.spotlight.domain.model.SongDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PlayerManager {
    val isPlayingFlow: StateFlow<Boolean>
    val currentPositionFlow: Flow<Long>
    val currentSongFlow: StateFlow<SongDetails?>
    val durationFlow: StateFlow<Long>
    val currentMediaMetadata: StateFlow<MediaMetadata?>

    fun setController(mediaController: MediaController)

    fun playAlbum(items: List<SongDetails>)

    fun next()

    fun prev()

    fun playOrPause()
}
