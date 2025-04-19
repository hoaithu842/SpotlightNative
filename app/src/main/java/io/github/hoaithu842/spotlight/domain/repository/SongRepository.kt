package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.SongInfo

interface SongRepository {
    suspend fun getSongInfo(id: String): ApiResponse<SongInfo>
}
