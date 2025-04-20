package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.domain.model.SongDetails
import io.github.hoaithu842.spotlight.domain.model.SongInfo
import io.github.hoaithu842.spotlight.domain.repository.FavoriteRepository
import io.github.hoaithu842.spotlight.manager.PlayerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryFavoriteViewModel
    @Inject
    constructor(
        private val playerManager: PlayerManager,
        private val favoriteRepository: FavoriteRepository,
    ) : ViewModel() {
        private val _libraryFavoriteUiState: MutableStateFlow<LibraryFavoriteUiState> =
            MutableStateFlow(LibraryFavoriteUiState.Loading)
        val libraryFavoriteUiState: StateFlow<LibraryFavoriteUiState> =
            _libraryFavoriteUiState.asStateFlow()

        init {
            viewModelScope.launch {
                when (val response = favoriteRepository.getFavoriteSongs()) {
                    is ApiResponse.Error -> _libraryFavoriteUiState.update { LibraryFavoriteUiState.Error }
                    is ApiResponse.Exception -> _libraryFavoriteUiState.update { LibraryFavoriteUiState.Error }
                    is ApiResponse.Success ->
                        _libraryFavoriteUiState.update {
                            LibraryFavoriteUiState.Success(
                                response.data,
                            )
                        }
                }
            }
        }

        fun playAlbum(items: List<SongInfo>?) {
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

fun List<SongInfo>.toSongDetailsList(): List<SongDetails> {
    return this.map { songInfo ->
        SongDetails(
            id = songInfo.id,
            title = songInfo.title,
            image = songInfo.image,
            song =
                Song(
                    id = songInfo.id ?: "",
                    name = songInfo.title ?: "",
                    url = songInfo.url ?: "",
                ),
        )
    }
}

sealed class LibraryFavoriteUiState {
    data object Loading : LibraryFavoriteUiState()

    data object Error : LibraryFavoriteUiState()

    class Success(val items: List<SongInfo>) : LibraryFavoriteUiState()
}
