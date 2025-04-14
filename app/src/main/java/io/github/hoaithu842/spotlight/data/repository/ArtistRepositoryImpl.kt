package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.data.repository.fakeData.fakeArtistDetails
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.ArtistDetails
import io.github.hoaithu842.spotlight.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl
    @Inject
    constructor(
        private val apiService: SpotlightApiService,
    ) : ArtistRepository {
        override suspend fun getArtist(): ApiResponse<ArtistDetails> {
            // TODO: return from ApiService
            return ApiResponse.Success(data = fakeArtistDetails.toDomain())
        }
    }
