package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.SongDetails
import io.github.hoaithu842.spotlight.domain.repository.AlbumRepository
import io.github.hoaithu842.spotlight.manager.PlayerManager
import io.github.hoaithu842.spotlight.navigation.RecommendationRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel
    @Inject
    constructor(
        private val playerManager: PlayerManager,
        private val albumRepository: AlbumRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _recommendationUiState: MutableStateFlow<RecommendationUiState> =
            MutableStateFlow(RecommendationUiState.Loading)
        val recommendationUiState: StateFlow<RecommendationUiState> =
            _recommendationUiState.asStateFlow()

        init {
            val recommendationRoute = savedStateHandle.toRoute<RecommendationRoute>()
            viewModelScope.launch {
                when (val response = albumRepository.getAlbum(recommendationRoute.id)) {
                    is ApiResponse.Error -> _recommendationUiState.update { RecommendationUiState.Error }
                    is ApiResponse.Exception -> _recommendationUiState.update { RecommendationUiState.Error }
                    is ApiResponse.Success ->
                        _recommendationUiState.update {
                            RecommendationUiState.Success(
                                response.data,
                            )
                        }
                }
            }
        }

        fun playAlbum(items: List<SongDetails>?) {
            viewModelScope.launch {
                if (!items.isNullOrEmpty()) {
                    playerManager.playAlbum(items)
                }
            }
        }
    }
