package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class FavoritePagingDto(
    @SerializedName("items")
    val items: List<SongInfoDto>?,
    @SerializedName("meta")
    val meta: DetailsPagingDto?,
)
