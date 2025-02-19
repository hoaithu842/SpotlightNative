package io.github.hoaithu842.spotlight_native.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hoaithu842.spotlight_native.manager.MediaPlayerController
import io.github.hoaithu842.spotlight_native.manager.NetworkMonitor
import io.github.hoaithu842.spotlight_native.manager.SpotlightConnectivityManager
import io.github.hoaithu842.spotlight_native.manager.SpotlightMediaPlayerController

@Module
@InstallIn(SingletonComponent::class)
abstract class ServicesModule {
    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: SpotlightConnectivityManager
    ): NetworkMonitor

    @Binds
    internal abstract fun bindsMediaPlayerController(
        playerController: SpotlightMediaPlayerController
    ): MediaPlayerController
}