package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.ArtistDetails
import io.github.hoaithu842.spotlight.domain.model.ArtistSong
import io.github.hoaithu842.spotlight.domain.model.ArtistSongs
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.domain.model.SongDetails
import io.github.hoaithu842.spotlight.domain.repository.ArtistRepository
import io.github.hoaithu842.spotlight.manager.PlayerManager
import io.github.hoaithu842.spotlight.navigation.ArtistRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel
    @Inject
    constructor(
        private val playerManager: PlayerManager,
        private val artistRepository: ArtistRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        val artistUiState: StateFlow<ArtistUiState> =
            flow {
                val artistRoute = savedStateHandle.toRoute<ArtistRoute>()
                emit(artistRepository.getArtist(artistRoute.id))
            }.map {
                when (it) {
                    is ApiResponse.Error -> ArtistUiState.Error
                    is ApiResponse.Exception -> ArtistUiState.Error
                    is ApiResponse.Success -> ArtistUiState.Success(it.data)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ArtistUiState.Loading,
            )

        val artistSongsUiState: StateFlow<ArtistSongsUiState> =
            flow {
                val artistRoute = savedStateHandle.toRoute<ArtistRoute>()
                emit(artistRepository.getArtistSongs(artistRoute.id))
            }.map {
                when (it) {
                    is ApiResponse.Error -> ArtistSongsUiState.Error
                    is ApiResponse.Exception -> ArtistSongsUiState.Error
                    is ApiResponse.Success -> ArtistSongsUiState.Success(it.data)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ArtistSongsUiState.Loading,
            )

        fun playAlbum(items: List<ArtistSong>?) {
            viewModelScope.launch {
                if (!items.isNullOrEmpty()) {
                    playerManager.playAlbum(items.toSongDetailsList())
                }
            }
        }

        fun pause() {
            viewModelScope.launch {
                playerManager.playOrPause()
            }
        }
    }

fun List<ArtistSong>.toSongDetailsList(): List<SongDetails> {
    return this.map { artistSong ->
        SongDetails(
            id = artistSong.id,
            title = artistSong.title,
            image = artistSong.image,
            song =
                Song(
                    id = artistSong.id,
                    name = artistSong.title,
                    url = artistSong.url,
                ),
        )
    }
}

sealed class ArtistUiState {
    data object Loading : ArtistUiState()

    data object Error : ArtistUiState()

    class Success(val artistDetails: ArtistDetails) : ArtistUiState()
}

sealed class ArtistSongsUiState {
    data object Loading : ArtistSongsUiState()

    data object Error : ArtistSongsUiState()

    class Success(val songsList: ArtistSongs) : ArtistSongsUiState()
}
