package com.cleverpumpkin.todoapp.data.remote.utils

import com.cleverpumpkin.todoapp.domain.models.Response
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

suspend inline fun <reified T> HttpClient.executeRequest(
    action: HttpClient.() -> HttpResponse
): Response<T> {
    val response = this.action()
    return if (response.status.isSuccess()) {
        val responseBody = response.bodyAsText()
        val typedResponse: T = Json.decodeFromString(responseBody)

        Response.Success(typedResponse)
    } else Response.Failure(response.status.value)
}
