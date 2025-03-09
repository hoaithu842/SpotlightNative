package io.github.hoaithu842.spotlight_native.data.network.callAdapter

import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CallAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        val callType = (returnType as? ParameterizedType)?.let { getParameterUpperBound(0, it) }
        if (callType?.let { getRawType(it) } != ApiResponse::class.java) {
            return null
        }

        val resultType = (callType as? ParameterizedType)?.let { getParameterUpperBound(0, it) }
        return resultType?.let { CallAdapter(it) }
    }

    companion object {
        fun create(): CallAdapterFactory = CallAdapterFactory()
    }
}
