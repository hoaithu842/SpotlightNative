package io.github.hoaithu842.spotlight.domain.model

import java.util.Date

data class UserProfile(
    val id: String?,
    val name: String?,
    val nickname: String?,
    val pictureURL: String?,
    val email: String?,
    val isEmailVerified: Boolean?,
    val familyName: String?,
    val createdAt: Date?,
)
