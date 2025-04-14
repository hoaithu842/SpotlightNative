package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) : ViewModel() {
        private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
        val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

        init {
            viewModelScope.launch {
                when (val response = homeRepository.getHomeContents()) {
                    is ApiResponse.Error -> _homeUiState.update { HomeUiState.Error }
                    is ApiResponse.Exception -> _homeUiState.update { HomeUiState.Error }
                    is ApiResponse.Success -> _homeUiState.update { HomeUiState.Success(response.data.contents) }
                }
            }
        }
    }
