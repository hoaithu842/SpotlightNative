package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.UserProfile
import io.github.hoaithu842.spotlight.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val apiService: SpotlightApiService,
    ) : UserRepository {
        override suspend fun getSessionUser(): ApiResponse<UserProfile> {
            return when (val response = apiService.getSessionUser()) {
                is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
                is ApiResponse.Exception -> ApiResponse.Exception(response.e)
                is ApiResponse.Success ->
                    ApiResponse.Success(
                        response.data.data.toDomain(),
                    )
            }
        }
    }
