package com.cleverpumpkin.networ.domain.connectivity_observer

/**
 * A status class representing network connection state
 */

sealed class Status {
    data object Available: Status()
    data object Unavailable: Status()
    data object Lost: Status()
}
