package io.github.hoaithu842.spotlight_native.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hoaithu842.spotlight_native.data.repository.PlayerRepositoryImpl
import io.github.hoaithu842.spotlight_native.data.repository.PreferencesRepositoryImpl
import io.github.hoaithu842.spotlight_native.data.repository.UserRepositoryImpl
import io.github.hoaithu842.spotlight_native.domain.repository.PlayerRepository
import io.github.hoaithu842.spotlight_native.domain.repository.PreferencesRepository
import io.github.hoaithu842.spotlight_native.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindsPlayerRepository(
        playerRepository: PlayerRepositoryImpl
    ): PlayerRepository

    @Binds
    internal abstract fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Binds
    internal abstract fun bindsPreferencesRepository(
        preferencesRepository: PreferencesRepositoryImpl
    ): PreferencesRepository
}