package io.github.hoaithu842.spotlight_native.domain.repository

import io.github.hoaithu842.spotlight_native.domain.model.Song

interface PlayerRepository {
    val songsList: List<Song>
}