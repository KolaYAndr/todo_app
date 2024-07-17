package com.cleverpumpkin.networ.domain.connectivity_observer

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe() : Flow<Status>

    fun wasPreviouslyOffline() : Boolean
}
