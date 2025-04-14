package io.github.hoaithu842.spotlight.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dictionaryDispatcher: SpotlightDispatchers)

enum class SpotlightDispatchers {
    Default,
    IO,
}
