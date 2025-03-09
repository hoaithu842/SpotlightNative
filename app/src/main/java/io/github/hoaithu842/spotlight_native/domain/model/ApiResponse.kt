package io.github.hoaithu842.spotlight_native.domain.model

sealed class ApiResponse<T : Any> {
    class Success<T : Any>(val data: T) : ApiResponse<T>()
    class Error<T : Any>(val code: Int, val message: String?) : ApiResponse<T>()
    class Exception<T : Any>(val e: Throwable) : ApiResponse<T>()
}
