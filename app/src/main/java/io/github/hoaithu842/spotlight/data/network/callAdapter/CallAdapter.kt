package io.github.hoaithu842.spotlight.data.network.callAdapter

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class CallAdapter(
    private val resultType: Type,
) : CallAdapter<Type, Call<ApiResponse<Type>>> {
    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> = NetworkResult(call)
}
