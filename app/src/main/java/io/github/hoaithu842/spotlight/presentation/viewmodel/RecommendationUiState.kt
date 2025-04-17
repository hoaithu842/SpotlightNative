package io.github.hoaithu842.spotlight.presentation.viewmodel

import io.github.hoaithu842.spotlight.domain.model.AlbumDetails

sealed class RecommendationUiState {
    data object Loading : RecommendationUiState()

    data object Error : RecommendationUiState()

    class Success(val album: AlbumDetails) : RecommendationUiState()
}
