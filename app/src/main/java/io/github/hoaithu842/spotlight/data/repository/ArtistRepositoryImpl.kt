package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.ArtistDetails
import io.github.hoaithu842.spotlight.domain.model.ArtistSongs
import io.github.hoaithu842.spotlight.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl
    @Inject
    constructor(
        private val apiService: SpotlightApiService,
    ) : ArtistRepository {
        override suspend fun getArtist(id: String): ApiResponse<ArtistDetails> {
            return when (val response = apiService.getArtist(id)) {
                is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
                is ApiResponse.Exception -> ApiResponse.Exception(response.e)
                is ApiResponse.Success -> ApiResponse.Success(response.data.data.toDomain())
            }
        }

        override suspend fun getArtistSongs(id: String): ApiResponse<ArtistSongs> {
            return when (val response = apiService.getArtistSongs(id)) {
                is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
                is ApiResponse.Exception -> ApiResponse.Exception(response.e)
                is ApiResponse.Success ->
                    ApiResponse.Success(
                        response.data.data.toDomain(),
                    )
            }
        }
    }
