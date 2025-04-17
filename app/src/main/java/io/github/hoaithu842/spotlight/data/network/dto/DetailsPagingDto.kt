package io.github.hoaithu842.spotlight.data.network.dto

data class DetailsPagingDto(
    val total: Int,
    val count: Int,
    val perPage: Int,
    val currentPage: Int,
    val totalPage: Int,
)
