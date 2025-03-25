package io.github.hoaithu842.spotlight_native.data.network.dto

import io.github.hoaithu842.spotlight_native.domain.model.UserProfile

fun UserProfileDto.toDomain(): UserProfile = UserProfile(
    name = this.name,
    pictureURL = this.picture,
    id = null,
    nickname = null,
    email = null,
    isEmailVerified = null,
    familyName = null,
    createdAt = null,
)
