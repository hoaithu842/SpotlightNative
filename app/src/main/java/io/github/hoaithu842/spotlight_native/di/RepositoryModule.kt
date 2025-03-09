package io.github.hoaithu842.spotlight_native.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hoaithu842.spotlight_native.data.repository.PlayerRepositoryImpl
import io.github.hoaithu842.spotlight_native.data.repository.SongRepositoryImpl
import io.github.hoaithu842.spotlight_native.domain.repository.PlayerRepository
import io.github.hoaithu842.spotlight_native.domain.repository.SongRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindsPlayerRepository(
        playerRepository: PlayerRepositoryImpl
    ): PlayerRepository

    @Binds
    internal abstract fun bindsSongRepository(
        songRepository: SongRepositoryImpl
    ): SongRepository
}