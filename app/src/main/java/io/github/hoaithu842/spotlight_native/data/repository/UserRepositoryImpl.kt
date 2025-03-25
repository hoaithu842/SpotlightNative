package io.github.hoaithu842.spotlight_native.data.repository

import io.github.hoaithu842.spotlight_native.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight_native.data.network.dto.SuccessBodyDto
import io.github.hoaithu842.spotlight_native.data.network.dto.UserProfileDto
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: SpotlightApiService,
) : UserRepository {
    override suspend fun getSessionUser(): ApiResponse<SuccessBodyDto<UserProfileDto>> = apiService.getSessionUser()
}