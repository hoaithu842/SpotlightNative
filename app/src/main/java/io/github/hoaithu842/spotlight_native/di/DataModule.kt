package io.github.hoaithu842.spotlight_native.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hoaithu842.spotlight_native.manager.NetworkMonitor
import io.github.hoaithu842.spotlight_native.manager.SpotlightConnectivityManager

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: SpotlightConnectivityManager
    ): NetworkMonitor
}