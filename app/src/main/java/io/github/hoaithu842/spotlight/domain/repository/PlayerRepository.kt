package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.SongDetails

interface PlayerRepository {
    val songsList: List<SongDetails>
}
