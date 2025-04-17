package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val searchRepository: SearchRepository,
    ) : ViewModel() {
        private val _searchUiState: MutableStateFlow<SearchUiState> =
            MutableStateFlow(SearchUiState.Loading)
        val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

        init {
            viewModelScope.launch {
                when (val response = searchRepository.getRecommendedPlaylists()) {
                    is ApiResponse.Error -> _searchUiState.update { SearchUiState.Error }
                    is ApiResponse.Exception -> _searchUiState.update { SearchUiState.Error }
                    is ApiResponse.Success -> _searchUiState.update { SearchUiState.Success(response.data) }
                }
            }
        }
    }
