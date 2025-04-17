package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.RecommendedPlaylists

interface SearchRepository {
    suspend fun getRecommendedPlaylists(): ApiResponse<RecommendedPlaylists>
}