package io.github.hoaithu842.spotlight_native.data.repository

import io.github.hoaithu842.spotlight_native.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight_native.data.network.dto.SongDetailDto
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.repository.SongRepository
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val apiService: SpotlightApiService,
) : SongRepository {
    override suspend fun getSong(): ApiResponse<SongDetailDto> = apiService.getPokemonList()
}