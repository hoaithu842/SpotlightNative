package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("avatar")
    val avatar: UserAvatarDto?,
    @SerializedName("email_verified")
    val emailVerified: Boolean?,
    @SerializedName("created_at")
    val createdAt: String?,
) {
    data class UserAvatarDto(
        @SerializedName("id")
        val id: String?,
        @SerializedName("url")
        val url: String?,
    )
}
