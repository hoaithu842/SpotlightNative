package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.SongInfo
import io.github.hoaithu842.spotlight.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl
    @Inject
    constructor(
        private val apiService: SpotlightApiService,
    ) : FavoriteRepository {
        override suspend fun getFavoriteSongs(): ApiResponse<List<SongInfo>> {
            return when (val response = apiService.getFavoriteSongs()) {
                is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
                is ApiResponse.Exception -> ApiResponse.Exception(response.e)
                is ApiResponse.Success -> ApiResponse.Success(response.data.data?.items?.map { it.toDomain() } ?: listOf())
            }
        }
    }
