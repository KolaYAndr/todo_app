package com.cleverpumpkin.auth.auth_screen

import androidx.lifecycle.ViewModel
import com.cleverpumpkin.auth.repository.AuthRepository
import com.yandex.authsdk.YandexAuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel responsible for handling authentication logic and managing UI state for the authentication screen.
 */

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<AuthScreenUiState> =
        MutableStateFlow(AuthScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        if (authRepository.canLogin()) _uiState.update { AuthScreenUiState.Logged }
        else _uiState.update { AuthScreenUiState.NotLogged }
    }

    fun yandexAuth(result: YandexAuthResult) {
        when (result) {
            YandexAuthResult.Cancelled -> _uiState.update { AuthScreenUiState.NotLogged }
            is YandexAuthResult.Failure -> _uiState.update { AuthScreenUiState.NotLogged }
            is YandexAuthResult.Success -> {
                authRepository.yandexAuth(result.token.value)
                _uiState.update { AuthScreenUiState.Logged }
            }
        }
    }

    fun apiKeyAuth() {
        authRepository.apiKeyAuth()
        _uiState.update { AuthScreenUiState.Logged }
    }
}
