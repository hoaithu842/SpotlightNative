package io.github.hoaithu842.spotlight_native.data.network.dto

import com.google.gson.annotations.SerializedName

data class SuccessBodyDto<T>(
    @SerializedName("error_code")
    val errorCode: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: T,
)