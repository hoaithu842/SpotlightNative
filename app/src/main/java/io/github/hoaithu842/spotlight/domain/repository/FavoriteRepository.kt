package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.SongInfo

interface FavoriteRepository {
    suspend fun getFavoriteSongs(): ApiResponse<List<SongInfo>>

    suspend fun addToFavorite(id: String): ApiResponse<Unit>

    suspend fun removeFromFavorite(id: String): ApiResponse<Unit>
}
