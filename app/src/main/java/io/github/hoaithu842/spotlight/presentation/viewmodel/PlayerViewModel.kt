package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.Artist
import io.github.hoaithu842.spotlight.domain.model.SongInfo
import io.github.hoaithu842.spotlight.domain.repository.SongRepository
import io.github.hoaithu842.spotlight.manager.PlayerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel
    @Inject
    constructor(
        private val songRepository: SongRepository,
        private val playerManager: PlayerManager,
    ) : ViewModel(), PlayerManager by playerManager {
        private val _playerUiState = MutableStateFlow(PlayerUiState())
        val playerUiState: StateFlow<PlayerUiState> = _playerUiState.asStateFlow()

        init {
//        observePlayerState()
            observeSongChanges()
            observePlaybackState()
            observePosition()
        }

        private fun observeSongChanges() {
            viewModelScope.launch {
                playerManager.currentSongFlow
                    .collectLatest { song ->
                        when (val response = songRepository.getSongInfo(song?.id ?: "")) {
                            is ApiResponse.Error -> {
                                _playerUiState.update {
                                    it.copy(
                                        songInfo =
                                            SongInfo(
                                                id = song?.id,
                                                title = song?.title,
                                                album = null,
                                                duration = 0,
                                                releaseDate = "",
                                                color = "",
                                                image = song?.image,
                                                categories = listOf(),
                                                artists = listOf(Artist(name = currentMediaMetadata.value?.artist.toString())),
                                            ),
                                    )
                                }
                            }

                            is ApiResponse.Exception -> {
                                _playerUiState.update {
                                    it.copy(
                                        songInfo =
                                            SongInfo(
                                                id = song?.id,
                                                title = song?.title,
                                                album = null,
                                                duration = 0,
                                                releaseDate = "",
                                                color = "",
                                                image = song?.image,
                                                categories = listOf(),
                                                artists = listOf(Artist(name = currentMediaMetadata.value?.artist.toString())),
                                            ),
                                    )
                                }
                            }

                            is ApiResponse.Success -> {
                                _playerUiState.update { it.copy(songInfo = response.data) }
                            }
                        }
                    }
            }
        }

        private fun observePlaybackState() {
            viewModelScope.launch {
                combine(
                    playerManager.isPlayingFlow,
                    playerManager.durationFlow,
                ) { isPlaying, duration ->
                    isPlaying to duration
                }.collect { (isPlaying, duration) ->
                    _playerUiState.update {
                        it.copy(
                            isPlaying = isPlaying,
                            duration = duration,
                        )
                    }
                }
            }
        }

        private fun observePosition() {
            viewModelScope.launch {
                playerManager.currentPositionFlow.collect { position ->
                    _playerUiState.update {
                        it.copy(position = position)
                    }
                }
            }
        }
    }

data class PlayerUiState(
    val songInfo: SongInfo? = null,
    val isPlaying: Boolean = false,
    val duration: Long = 0,
    val position: Long = 0,
)
