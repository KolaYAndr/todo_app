package com.cleverpumpkin.todoapp.presentation.utils

import com.cleverpumpkin.todoapp.R

fun getErrorStringResource(errorCode: Int) = when (errorCode) {
    404 -> R.string.network_error_404
    401 -> R.string.network_error_401
    403 -> R.string.network_error_403
    408 -> R.string.network_error_408
    500 -> R.string.network_error_500
    else -> R.string.unexpected_error
}
