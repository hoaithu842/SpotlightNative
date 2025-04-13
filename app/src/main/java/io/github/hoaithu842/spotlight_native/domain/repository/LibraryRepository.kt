package io.github.hoaithu842.spotlight_native.domain.repository

import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.model.LibraryContents

interface LibraryRepository {
    suspend fun getPlaylists(): ApiResponse<LibraryContents>
}
