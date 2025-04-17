package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.RecommendedPlaylists
import io.github.hoaithu842.spotlight.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl
    @Inject
    constructor(
        private val apiService: SpotlightApiService,
    ) : SearchRepository {
        override suspend fun getRecommendedPlaylists(): ApiResponse<RecommendedPlaylists> {
            return when (val response = apiService.getPlaylists()) {
                is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
                is ApiResponse.Exception -> ApiResponse.Exception(response.e)
                is ApiResponse.Success ->
                    ApiResponse.Success(
                        response.data.data.toDomain(),
                    )
            }
        }
    }
