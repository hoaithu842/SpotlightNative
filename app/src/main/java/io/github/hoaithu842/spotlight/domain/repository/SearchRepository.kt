package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.RecommendedPlaylists
import io.github.hoaithu842.spotlight.domain.model.SearchResult

interface SearchRepository {
    suspend fun getSearchResult(query: String): ApiResponse<SearchResult>

    suspend fun getRecommendedPlaylists(): ApiResponse<RecommendedPlaylists>
}
