package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.domain.model.AlbumDetails
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.repository.AlbumRepository
import javax.inject.Inject

class AlbumRepositoryImpl
    @Inject
    constructor(
        private val apiService: SpotlightApiService,
    ) : AlbumRepository {
        override suspend fun getAlbum(id: String): ApiResponse<AlbumDetails> {
            return when (val response = apiService.getAlbum(id)) {
                is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
                is ApiResponse.Exception -> ApiResponse.Exception(response.e)
                is ApiResponse.Success -> ApiResponse.Success(response.data.data.toDomain())
            }
        }
    }
