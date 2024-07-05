package com.cleverpumpkin.todoapp.domain.models

import kotlin.Exception

sealed class Response<out R> {
    data class Success<out R>(val result: R) : Response<R>()

    data class Failure(val exception: Exception): Response<Nothing>()
}
