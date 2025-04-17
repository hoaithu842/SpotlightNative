package io.github.hoaithu842.spotlight.presentation.viewmodel

import io.github.hoaithu842.spotlight.domain.model.RecommendedPlaylists

sealed class SearchUiState {
    data object Loading : SearchUiState()

    data object Error : SearchUiState()

    class Success(val playlists: RecommendedPlaylists) : SearchUiState()
}