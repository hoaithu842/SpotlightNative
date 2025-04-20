package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.SongInfo
import io.github.hoaithu842.spotlight.domain.repository.FavoriteRepository
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
    }

sealed class LibraryFavoriteUiState {
    data object Loading : LibraryFavoriteUiState()

    data object Error : LibraryFavoriteUiState()

    class Success(val items: List<SongInfo>) : LibraryFavoriteUiState()
}
