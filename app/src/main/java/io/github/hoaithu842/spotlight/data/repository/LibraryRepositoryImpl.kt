package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.data.repository.fakeData.fakeLibraryDetails
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.LibraryContents
import io.github.hoaithu842.spotlight.domain.repository.LibraryRepository
import javax.inject.Inject

class LibraryRepositoryImpl
    @Inject
    constructor(
//        private val apiService: SpotlightApiService,
    ) : LibraryRepository {
        override suspend fun getLibrary(): ApiResponse<LibraryContents> {
            return ApiResponse.Success(data = fakeLibraryDetails.toDomain())
        }
    }
