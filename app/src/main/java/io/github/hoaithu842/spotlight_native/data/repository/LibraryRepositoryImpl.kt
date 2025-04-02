package io.github.hoaithu842.spotlight_native.data.repository

import io.github.hoaithu842.spotlight_native.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight_native.data.network.dto.toDomain
import io.github.hoaithu842.spotlight_native.data.repository.fakeData.fakeLibraryDetails
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.model.LibraryContents
import io.github.hoaithu842.spotlight_native.domain.repository.LibraryRepository
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val apiService: SpotlightApiService,
) : LibraryRepository {
    override suspend fun getPlaylists(): ApiResponse<LibraryContents> {
        // TODO: return from ApiService
        return ApiResponse.Success(fakeLibraryDetails.toDomain())
    }
}