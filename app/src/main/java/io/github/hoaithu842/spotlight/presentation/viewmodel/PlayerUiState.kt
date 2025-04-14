package io.github.hoaithu842.spotlight.presentation.viewmodel

import io.github.hoaithu842.spotlight.domain.model.SongDetails

data class PlayerUiState(
    val isPlaying: Boolean,
    val duration: Long,
    val currentSong: SongDetails,
)
