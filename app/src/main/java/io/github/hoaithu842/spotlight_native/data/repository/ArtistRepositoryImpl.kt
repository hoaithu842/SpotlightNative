package io.github.hoaithu842.spotlight_native.data.repository

import io.github.hoaithu842.spotlight_native.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight_native.data.network.dto.toDomain
import io.github.hoaithu842.spotlight_native.data.repository.fakeData.fakeArtistDetails
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.model.ArtistDetails
import io.github.hoaithu842.spotlight_native.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val apiService: SpotlightApiService,
) : ArtistRepository {
    override suspend fun getArtist(): ApiResponse<ArtistDetails> {
        // TODO: return from ApiService
        return ApiResponse.Success(data = fakeArtistDetails.toDomain())
    }
}