package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class DetailsPagingDto(
    @SerializedName("total")
    val total: Int?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("total_page")
    val totalPage: Int?,
)
