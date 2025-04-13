package io.github.hoaithu842.spotlight_native.manager

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
