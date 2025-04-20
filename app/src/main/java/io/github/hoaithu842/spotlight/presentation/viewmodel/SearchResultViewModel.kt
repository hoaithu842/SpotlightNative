package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.SearchResult
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.domain.model.SongDetails
import io.github.hoaithu842.spotlight.domain.model.SongSearchResult
import io.github.hoaithu842.spotlight.domain.repository.SearchRepository
import io.github.hoaithu842.spotlight.domain.repository.SongRepository
import io.github.hoaithu842.spotlight.manager.PlayerManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel
    @Inject
    constructor(
        private val playerManager: PlayerManager,
        private val songRepository: SongRepository,
        private val searchRepository: SearchRepository,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        val searchResultUiState: StateFlow<SearchResultUiState> =
            getSearchResult().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SearchResultUiState.Loading,
            )

        @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
        private fun getSearchResult(): Flow<SearchResultUiState> =
            savedStateHandle.getStateFlow("search_query", "").debounce(500)
                .flatMapLatest {
                    flow {
                        emit(searchRepository.getSearchResult(it))
                    }.map {
                        when (it) {
                            is ApiResponse.Error -> SearchResultUiState.Error
                            is ApiResponse.Exception -> SearchResultUiState.Error
                            is ApiResponse.Success -> SearchResultUiState.Success(it.data)
                        }
                    }
                }

        fun onChangeSearchQuery(query: String) {
            savedStateHandle["search_query"] = query
        }

        fun playSong(item: SongSearchResult?) {
            viewModelScope.launch {
                if (item != null) {
                    val response = songRepository.getSongInfo(item.id ?: "")
                    when (response) {
                        is ApiResponse.Error -> {}
                        is ApiResponse.Exception -> {}
                        is ApiResponse.Success -> {
                            val songInfo = response.data
                            playerManager.playAlbum(
                                listOf(
                                    SongDetails(
                                        id = songInfo.id,
                                        title = songInfo.title,
                                        image = songInfo.image,
                                        song =
                                            Song(
                                                id = songInfo.id ?: "",
                                                name = songInfo.title ?: "",
                                                url = songInfo.url ?: "",
                                            ),
                                    ),
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

sealed class SearchResultUiState {
    data object Loading : SearchResultUiState()

    data object Error : SearchResultUiState()

    class Success(val result: SearchResult) : SearchResultUiState()
}
