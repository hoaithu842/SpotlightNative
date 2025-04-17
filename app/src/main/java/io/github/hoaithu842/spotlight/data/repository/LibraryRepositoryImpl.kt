package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.LibraryContents
import io.github.hoaithu842.spotlight.domain.repository.LibraryRepository
import javax.inject.Inject

class LibraryRepositoryImpl
    @Inject
    constructor(
        private val apiService: SpotlightApiService,
    ) : LibraryRepository {
        override suspend fun getPlaylists(): ApiResponse<LibraryContents> {
            return when (val response = apiService.getPlaylists()) {
                is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
                is ApiResponse.Exception -> ApiResponse.Exception(response.e)
                is ApiResponse.Success -> ApiResponse.Success(data = response.data.data.toDomain())
            }
        }
    }
