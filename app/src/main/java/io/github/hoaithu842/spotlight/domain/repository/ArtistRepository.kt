package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.ArtistDetails

interface ArtistRepository {
    suspend fun getArtist(): ApiResponse<ArtistDetails>
}
