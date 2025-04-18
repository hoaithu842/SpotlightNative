package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class ArtistSongsPagingDto(
    @SerializedName("items")
    val items: List<ArtistSongDto>?,
    @SerializedName("meta")
    val meta: DetailsPagingDto,
)
