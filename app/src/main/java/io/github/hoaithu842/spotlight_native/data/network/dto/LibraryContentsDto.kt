package io.github.hoaithu842.spotlight_native.data.network.dto

import com.google.gson.annotations.SerializedName

data class LibraryContentsDto(
    @SerializedName("items")
    val items: List<LibraryItemDto>?,
)
