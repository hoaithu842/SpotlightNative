package io.github.hoaithu842.spotlight_native.presentation.viewmodel

import io.github.hoaithu842.spotlight_native.domain.model.HomeSection

sealed class HomeUiState {
    data object Loading : HomeUiState()

    data object Error : HomeUiState()

    class Success(val contents: List<HomeSection>) : HomeUiState()
}
