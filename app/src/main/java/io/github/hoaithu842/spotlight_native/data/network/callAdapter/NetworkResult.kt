package io.github.hoaithu842.spotlight_native.data.network.callAdapter

import io.github.hoaithu842.spotlight_native.data.network.callAdapter.CallAdapterUtils.handleApi
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResult<T : Any>(
    private val proxy: Call<T>
) : Call<ApiResponse<T>> {
    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = handleApi { response }
                callback.onResponse(this@NetworkResult, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = ApiResponse.Exception<T>(e = t)
                callback.onResponse(this@NetworkResult, Response.success(networkResult))
            }
        })
    }

    override fun execute(): Response<ApiResponse<T>> = throw NotImplementedError()
    override fun clone(): Call<ApiResponse<T>> = NetworkResult(proxy.clone())
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() {
        proxy.cancel()
    }
}