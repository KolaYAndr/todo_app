package com.cleverpumpkin.network.domain.connectivity_observer

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe() : Flow<Status>

    fun wasPreviouslyOffline() : Boolean
}
