package com.cleverpumpkin.todoapp.domain.models

/**
 * A sealed class representing the result of an operation, either Success or Failure.
 */
sealed class Response<out R> {
    data class Success<out R>(val result: R) : Response<R>()

    data class Failure(val exceptionCode: Int): Response<Nothing>()
}
