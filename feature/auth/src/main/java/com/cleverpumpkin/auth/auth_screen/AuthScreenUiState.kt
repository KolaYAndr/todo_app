package com.cleverpumpkin.auth.auth_screen

/**
 * Represents the UI state of the authentication screen.
 */

sealed class AuthScreenUiState {
    data object Loading : AuthScreenUiState()
    data object NotLogged : AuthScreenUiState()
    data object Logged : AuthScreenUiState()
}
