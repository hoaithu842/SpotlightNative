package io.github.hoaithu842.spotlight.manager

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.MediaController
import io.github.hoaithu842.spotlight.domain.model.SongDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotlightPlayerManager
    @Inject
    constructor() : PlayerManager {
        private var _mediaController: MediaController? = null
        private var _songsList: List<SongDetails> = emptyList()

        private val _isPlayingFlow = MutableStateFlow(false)
        override val isPlayingFlow: StateFlow<Boolean> = _isPlayingFlow.asStateFlow()

        override val currentPositionFlow: Flow<Long> =
            flow {
                while (true) {
                    _mediaController?.let {
                        if (it.isPlaying) emit(it.currentPosition)
                    }
                    delay(500)
                }
            }.flowOn(Dispatchers.Main)

        private val _durationFlow = MutableStateFlow<Long>(0)
        override val durationFlow: StateFlow<Long> = _durationFlow.asStateFlow()

        private val _mediaMetadataFlow = MutableStateFlow<MediaMetadata?>(null)
        override val currentMediaMetadata: StateFlow<MediaMetadata?> = _mediaMetadataFlow.asStateFlow()

        private val _currentSongFlow = MutableStateFlow(_songsList.firstOrNull())
        override val currentSongFlow: StateFlow<SongDetails?> = _currentSongFlow.asStateFlow()

        override fun setController(mediaController: MediaController) {
            _mediaController = mediaController
            mediaController.addListener(
                object : Player.Listener {
                    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                        super.onMediaMetadataChanged(mediaMetadata)
                        _mediaMetadataFlow.value = mediaMetadata
                    }

                    override fun onTracksChanged(tracks: Tracks) {
                        super.onTracksChanged(tracks)
                        _durationFlow.value = _mediaController?.duration ?: 0
                    }

                    override fun onIsLoadingChanged(isLoading: Boolean) {
                        super.onIsLoadingChanged(isLoading)
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        super.onIsPlayingChanged(isPlaying)
                        _isPlayingFlow.value = isPlaying
                    }

                    override fun onMediaItemTransition(
                        mediaItem: MediaItem?,
                        reason: Int,
                    ) {
                        super.onMediaItemTransition(mediaItem, reason)
                        _mediaController?.currentMediaItemIndex?.let { index ->
                            _songsList.getOrNull(index)?.let { song ->
                                _currentSongFlow.value = song
                            }
                        }
                    }
                },
            )
        }

        override fun playAlbum(items: List<SongDetails>) {
            _mediaController?.clearMediaItems()
            items.forEach {
                if (!it.song?.url.isNullOrEmpty()) {
                    _mediaController?.addMediaItem(
                        MediaItem.fromUri(
                            it.song?.url ?: "",
                        ),
                    )
                }
            }
            _songsList = items
            _currentSongFlow.value = items.firstOrNull()
            _mediaController?.prepare()
            _mediaController?.play()
        }

        override fun next() {
            _mediaController?.seekToNextMediaItem()
        }

        override fun prev() {
            _mediaController?.seekToPreviousMediaItem()
        }

        override fun playOrPause() {
            if (_mediaController?.isPlaying != false) {
                _mediaController?.pause()
            } else {
                _mediaController?.play()
            }
        }
    }
