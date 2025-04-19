package io.github.hoaithu842.spotlight.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hoaithu842.spotlight.manager.PlayerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel
    @Inject
    constructor(
        private val playerManager: PlayerManager,
    ) : ViewModel(), PlayerManager by playerManager {
        private val _playerUiState: MutableStateFlow<PlayerUiState> =
            MutableStateFlow(
                PlayerUiState(
                    isPlaying = false,
                    duration = 0,
                ),
            )
        val playerUiState: StateFlow<PlayerUiState> = _playerUiState.asStateFlow()
    }
