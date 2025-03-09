package io.github.hoaithu842.spotlight_native.data.network

import io.github.hoaithu842.spotlight_native.data.network.dto.SongDetailDto
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import retrofit2.http.GET

interface SpotlightApiService {
    @GET("pokemon?limit=10000&offset=0")
    suspend fun getPokemonList(): ApiResponse<SongDetailDto>
}