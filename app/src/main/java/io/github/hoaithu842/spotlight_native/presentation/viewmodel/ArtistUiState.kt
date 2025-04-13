package io.github.hoaithu842.spotlight_native.presentation.viewmodel

import io.github.hoaithu842.spotlight_native.domain.model.ArtistDetails

sealed class ArtistUiState {
    data object Loading : ArtistUiState()

    data object Error : ArtistUiState()

    class Success(val artistDetails: ArtistDetails) : ArtistUiState()
}
