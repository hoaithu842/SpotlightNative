package io.github.hoaithu842.spotlight.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.ui.util.trace
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

internal class SpotlightConnectivityManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : NetworkMonitor {
        override val isOnline: Flow<Boolean> =
            callbackFlow {
                trace("NetworkMonitor.callbackFlow") {
                    val connectivityManager = context.getSystemService<ConnectivityManager>()
                    if (connectivityManager == null) {
                        channel.trySend(false)
                        channel.close()
                        return@callbackFlow
                    }

                    val callback =
                        object : NetworkCallback() {
                            private val networks = mutableSetOf<Network>()

                            override fun onAvailable(network: Network) {
                                networks += network
                                channel.trySend(true)
                            }

                            override fun onLost(network: Network) {
                                networks -= network
                                channel.trySend(networks.isNotEmpty())
                            }
                        }

                    trace("NetworkMonitor.registerNetworkCallback") {
                        val request =
                            NetworkRequest.Builder()
                                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                                .build()
                        connectivityManager.registerNetworkCallback(request, callback)
                    }

                    channel.trySend(connectivityManager.isCurrentlyConnected())

                    awaitClose {
                        connectivityManager.unregisterNetworkCallback(callback)
                    }
                }
            }

        private fun ConnectivityManager.isCurrentlyConnected() =
            activeNetwork
                ?.let(::getNetworkCapabilities)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }
