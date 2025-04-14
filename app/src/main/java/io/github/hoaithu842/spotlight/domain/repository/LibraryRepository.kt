package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.LibraryContents

interface LibraryRepository {
    suspend fun getPlaylists(): ApiResponse<LibraryContents>
}
