package com.cleverpumpkin.networ.data.utils

import com.cleverpumpkin.networ.domain.response_wrapper.Response
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import java.net.UnknownHostException

suspend inline fun <reified T> HttpClient.executeRequest(
    action: HttpClient.() -> HttpResponse
): Response<T> {
    try {
        val response = action()
        return if (response.status.isSuccess()) {
            val responseBody = response.bodyAsText()
            val typedResponse: T = Json.decodeFromString(responseBody)

            Response.Success(typedResponse)
        } else Response.Failure(response.status.value)
    }
    catch (e: Exception) {
        return Response.Failure(0)
    }
}
