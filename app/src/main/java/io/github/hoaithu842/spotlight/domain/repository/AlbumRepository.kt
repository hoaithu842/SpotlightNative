package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.AlbumDetails
import io.github.hoaithu842.spotlight.domain.model.ApiResponse

interface AlbumRepository {
    suspend fun getAlbum(id: String): ApiResponse<AlbumDetails>
}
