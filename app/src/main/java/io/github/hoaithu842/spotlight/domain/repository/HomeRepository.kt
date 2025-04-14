package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.HomeContents

interface HomeRepository {
    suspend fun getHomeContents(): ApiResponse<HomeContents>
}
