package com.cleverpumpkin.todoapp.domain.repository

interface AuthRepository {
    fun yandexAuth(yandexToken: String)

    fun apiKeyAuth()

    fun getToken() : String

    fun canLogin(): Boolean
}
