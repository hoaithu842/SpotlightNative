package io.github.hoaithu842.spotlight_native.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hoaithu842.spotlight_native.data.network.SpotlightApiService
import io.github.hoaithu842.spotlight_native.data.network.callAdapter.CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
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