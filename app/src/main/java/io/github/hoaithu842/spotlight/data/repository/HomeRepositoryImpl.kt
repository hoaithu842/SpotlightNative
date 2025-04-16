package io.github.hoaithu842.spotlight.data.repository

import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.dto.toDomain
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.HomeContents
import io.github.hoaithu842.spotlight.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl
@Inject
constructor(
    private val apiService: SpotlightApiService,
) : HomeRepository {
    override suspend fun getHomeContents(): ApiResponse<HomeContents> {
        // TODO: return from ApiService
        return when (val response = apiService.getHomeContents()) {
            is ApiResponse.Error -> ApiResponse.Error(response.code, response.message)
            is ApiResponse.Exception -> ApiResponse.Exception(response.e)
            is ApiResponse.Success -> ApiResponse.Success(
                HomeContents(contents = response.data.data?.map { it.toDomain() } ?: listOf())
            )
        }
    }
}
