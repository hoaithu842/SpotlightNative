package io.github.hoaithu842.spotlight.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hoaithu842.spotlight.data.repository.AlbumRepositoryImpl
import io.github.hoaithu842.spotlight.data.repository.ArtistRepositoryImpl
import io.github.hoaithu842.spotlight.data.repository.FavoriteRepositoryImpl
import io.github.hoaithu842.spotlight.data.repository.HomeRepositoryImpl
import io.github.hoaithu842.spotlight.data.repository.LibraryRepositoryImpl
import io.github.hoaithu842.spotlight.data.repository.PreferencesRepositoryImpl
import io.github.hoaithu842.spotlight.data.repository.SearchRepositoryImpl
import io.github.hoaithu842.spotlight.data.repository.SongRepositoryImpl
import io.github.hoaithu842.spotlight.data.repository.UserRepositoryImpl
import io.github.hoaithu842.spotlight.domain.repository.AlbumRepository
import io.github.hoaithu842.spotlight.domain.repository.ArtistRepository
import io.github.hoaithu842.spotlight.domain.repository.FavoriteRepository
import io.github.hoaithu842.spotlight.domain.repository.HomeRepository
import io.github.hoaithu842.spotlight.domain.repository.LibraryRepository
import io.github.hoaithu842.spotlight.domain.repository.PreferencesRepository
import io.github.hoaithu842.spotlight.domain.repository.SearchRepository
import io.github.hoaithu842.spotlight.domain.repository.SongRepository
import io.github.hoaithu842.spotlight.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindsPlayerRepository(playerRepository: SongRepositoryImpl): SongRepository

    @Binds
    internal abstract fun bindsUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    internal abstract fun bindsPreferencesRepository(preferencesRepository: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    internal abstract fun bindsHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository

    @Binds
    internal abstract fun bindsArtistRepository(artistRepository: ArtistRepositoryImpl): ArtistRepository

    @Binds
    internal abstract fun bindsLibraryRepository(libraryRepository: LibraryRepositoryImpl): LibraryRepository

    @Binds
    internal abstract fun bindsSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

    @Binds
    internal abstract fun bindsAlbumRepository(albumRepository: AlbumRepositoryImpl): AlbumRepository

    @Binds
    internal abstract fun bindsFavoriteRepository(favoriteRepository: FavoriteRepositoryImpl): FavoriteRepository
}
