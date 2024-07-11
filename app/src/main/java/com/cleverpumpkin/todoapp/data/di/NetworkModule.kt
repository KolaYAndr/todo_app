package com.cleverpumpkin.todoapp.data.di

import android.util.Log
import com.cleverpumpkin.todoapp.data.connectivity_observer.NetworkObserver
import com.cleverpumpkin.todoapp.data.remote.api.TodoApi
import com.cleverpumpkin.todoapp.data.remote.api.TodoApiImpl
import com.cleverpumpkin.todoapp.domain.connectivity_observer.ConnectivityObserver
import com.cleverpumpkin.todoapp.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    @Singleton
    fun bindNetworkObserver(impl: NetworkObserver): ConnectivityObserver

    @Binds
    @Singleton
    fun bindTodoApi(impl: TodoApiImpl): TodoApi

    companion object {

        private const val MAX_RETRIES = 3

        @Provides
        @Singleton
        fun provideHttpClient(authRepository: AuthRepository) : HttpClient {
            return HttpClient(Android) {
                install(HttpRequestRetry) {
                    retryOnServerErrors(maxRetries = MAX_RETRIES)
                    exponentialDelay()
                }
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.d("logger message", message)
                        }
                    }

                }
                install(ContentNegotiation) {
                    json()
                }
                install(DefaultRequest) {
                    header(HttpHeaders.Authorization, authRepository.getToken())
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    header(HttpHeaders.Accept, ContentType.Application.Json)
                }
            }
        }
    }
}
