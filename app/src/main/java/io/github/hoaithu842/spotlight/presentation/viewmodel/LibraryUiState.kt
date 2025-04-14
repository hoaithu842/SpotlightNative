package io.github.hoaithu842.spotlight.presentation.viewmodel

import io.github.hoaithu842.spotlight.domain.model.LibraryContents

sealed class LibraryUiState {
    data object Loading : LibraryUiState()

    data object Error : LibraryUiState()

    class Success(val libraryContents: LibraryContents) : LibraryUiState()
}
