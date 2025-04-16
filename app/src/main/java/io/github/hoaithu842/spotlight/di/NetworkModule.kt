package io.github.hoaithu842.spotlight.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hoaithu842.spotlight.data.datastore.SpotlightPreferences
import io.github.hoaithu842.spotlight.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight.data.network.callAdapter.CallAdapterFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://spotlighthcmus.io.vn/api/v1/"

    @Provides
    @Singleton
    fun providesAuthorizationInterceptor(spotlightPreferences: SpotlightPreferences): Interceptor {
        return Interceptor { chain ->
            val token =
                runBlocking {
                    spotlightPreferences.accessTokenFlow.firstOrNull() ?: ""
                }

            Log.d("Rachel", "Fetching with $token")

            val request =
                chain.request().newBuilder().addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer $token")
                    .build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesDictionaryOkHttp(authorizationInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSpotlightApiService(retrofit: Retrofit): SpotlightApiService {
        return retrofit.create(SpotlightApiService::class.java)
    }
}
