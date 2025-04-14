package io.github.hoaithu842.spotlight.presentation.viewmodel

import io.github.hoaithu842.spotlight.domain.model.ArtistDetails

sealed class ArtistUiState {
    data object Loading : ArtistUiState()

    data object Error : ArtistUiState()

    class Success(val artistDetails: ArtistDetails) : ArtistUiState()
}
