package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel
    @Inject
    constructor(
        private val libraryRepository: LibraryRepository,
    ) : ViewModel() {
        val libraryUiState: StateFlow<LibraryUiState> =
            flow {
                emit(libraryRepository.getPlaylists())
            }.map {
                when (it) {
                    is ApiResponse.Error -> LibraryUiState.Error
                    is ApiResponse.Exception -> LibraryUiState.Error
                    is ApiResponse.Success -> LibraryUiState.Success(it.data)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = LibraryUiState.Loading,
            )
    }
