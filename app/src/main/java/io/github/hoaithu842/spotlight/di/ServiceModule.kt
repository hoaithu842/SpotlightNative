package io.github.hoaithu842.spotlight.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hoaithu842.spotlight.manager.AccountManager
import io.github.hoaithu842.spotlight.manager.NetworkMonitor
import io.github.hoaithu842.spotlight.manager.SpotlightAccountManager
import io.github.hoaithu842.spotlight.manager.SpotlightConnectivityManager

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    internal abstract fun bindsNetworkMonitor(networkMonitor: SpotlightConnectivityManager): NetworkMonitor

    @Binds
    internal abstract fun bindsAccountManager(accountManager: SpotlightAccountManager): AccountManager
}
