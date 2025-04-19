package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.SongInfo
import io.github.hoaithu842.spotlight.domain.repository.SongRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRepositoryImpl
    @Inject
    constructor(
        private val apiService: SpotlightApiService,
    ) : SongRepository {
        override suspend fun getSongInfo(id: String): ApiResponse<SongInfo> {
            return when (val response = apiService.getSongInfo(id)) {
                is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
                is ApiResponse.Exception -> ApiResponse.Exception(response.e)
                is ApiResponse.Success -> ApiResponse.Success(response.data.data.toDomain())
            }
        }
    }
