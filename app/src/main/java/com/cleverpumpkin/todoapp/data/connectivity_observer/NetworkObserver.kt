package com.cleverpumpkin.todoapp.data.connectivity_observer

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.cleverpumpkin.todoapp.domain.connectivity_observer.ConnectivityObserver
import com.cleverpumpkin.todoapp.domain.connectivity_observer.Status
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A class responsible for observing network connection state
 * @see Status
 */

class NetworkObserver @Inject constructor(@ApplicationContext private val context: Context) :
    ConnectivityObserver {
    private var wasOffline: Boolean = false

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(Status.Available) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    wasOffline = true
                    launch { send(Status.Lost) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    wasOffline = true
                    launch { send(Status.Unavailable) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

    override fun wasPreviouslyOffline(): Boolean = wasOffline
}