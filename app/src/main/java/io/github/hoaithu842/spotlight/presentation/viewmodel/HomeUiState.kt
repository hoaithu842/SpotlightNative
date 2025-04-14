package io.github.hoaithu842.spotlight.presentation.viewmodel

import io.github.hoaithu842.spotlight.domain.model.HomeSection

sealed class HomeUiState {
    data object Loading : HomeUiState()

    data object Error : HomeUiState()

    class Success(val contents: List<HomeSection>) : HomeUiState()
}
