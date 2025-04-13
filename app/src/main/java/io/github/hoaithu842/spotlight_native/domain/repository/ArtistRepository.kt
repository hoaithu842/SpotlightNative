package io.github.hoaithu842.spotlight_native.domain.repository

import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.model.ArtistDetails

interface ArtistRepository {
    suspend fun getArtist(): ApiResponse<ArtistDetails>
}
