package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.ArtistDetails
import io.github.hoaithu842.spotlight.domain.model.ArtistSongs

interface ArtistRepository {
    suspend fun getArtist(id: String): ApiResponse<ArtistDetails>

    suspend fun getArtistSongs(id: String): ApiResponse<ArtistSongs>
}
