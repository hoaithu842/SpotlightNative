package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class PlaylistsPagingDto(
    @SerializedName("items")
    val items: List<PlaylistDto>?,
    @SerializedName("meta")
    val meta: PagingDetailsDto,
) {
    data class PagingDetailsDto(
        val total: Int,
        val count: Int,
        val perPage: Int,
        val currentPage: Int,
        val totalPage: Int,
    )
}