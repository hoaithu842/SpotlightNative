package io.github.hoaithu842.spotlight_native.presentation.viewmodel

import io.github.hoaithu842.spotlight_native.domain.model.SongDetails

data class PlayerUiState(
    val isPlaying: Boolean,
    val duration: Long,
    val currentSong: SongDetails,
)
