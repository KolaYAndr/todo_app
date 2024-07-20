package com.cleverpumpkin.network.domain.response_wrapper

/**
 * A sealed class representing the result of an operation, either Success or Failure.
 */
sealed class Response<out R> {
    data class Success<out R>(val result: R) : Response<R>()

    data class Failure(val exceptionCode: Int): Response<Nothing>()
}
