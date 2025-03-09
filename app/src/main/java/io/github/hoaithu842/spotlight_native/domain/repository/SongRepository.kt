package io.github.hoaithu842.spotlight_native.domain.repository

import io.github.hoaithu842.spotlight_native.data.network.dto.SongDetailDto
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse

interface SongRepository {
    suspend fun getSong(): ApiResponse<SongDetailDto> // TODO: Mapper to domain model
}