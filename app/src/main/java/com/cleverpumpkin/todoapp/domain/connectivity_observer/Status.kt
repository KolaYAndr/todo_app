package com.cleverpumpkin.todoapp.domain.connectivity_observer

/**
 * A status class representing network connection state
 */

sealed class Status {
    data object Available: Status()
    data object Unavailable: Status()
    data object Losing: Status()
    data object Lost: Status()
}
