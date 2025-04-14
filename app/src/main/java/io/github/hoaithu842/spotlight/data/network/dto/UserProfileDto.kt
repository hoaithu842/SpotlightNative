package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String,
)
