package io.github.hoaithu842.spotlight_native.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.repository.ArtistRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
) : ViewModel() {

    val artistUiState: StateFlow<ArtistUiState> =
        flow {
            emit(artistRepository.getArtist())
        }.map {
            when (it) {
                is ApiResponse.Error -> ArtistUiState.Error
                is ApiResponse.Exception -> ArtistUiState.Error
                is ApiResponse.Success -> ArtistUiState.Success(it.data)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ArtistUiState.Loading
        )
}