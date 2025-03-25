package io.github.hoaithu842.spotlight_native.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dictionaryDispatcher: SpotlightDispatchers)

enum class SpotlightDispatchers {
    Default,
    IO,
}
