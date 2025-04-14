package io.github.hoaithu842.spotlight.data.network.callAdapter

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import retrofit2.HttpException
import retrofit2.Response

object CallAdapterUtils {
    fun <T : Any> handleApi(execute: () -> Response<T>): ApiResponse<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ApiResponse.Success(body)
            } else {
                ApiResponse.Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            ApiResponse.Error(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            ApiResponse.Exception(e)
        }
    }
}
