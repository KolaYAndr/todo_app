package com.cleverpumpkin.todoapp.data.repository

import android.content.SharedPreferences
import com.cleverpumpkin.todoapp.data.preferences.PreferenceKeys
import com.cleverpumpkin.todoapp.data.remote.api.ApiKey
import com.cleverpumpkin.todoapp.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Implementation of [AuthRepository] using SharedPreferences for authentication.
 */

class AuthRepositoryImpl @Inject constructor(private val prefs: SharedPreferences) :
    AuthRepository {

    override fun yandexAuth(yandexToken: String) {
        prefs.edit().putString(PreferenceKeys.AUTH_KEY, "OAuth $yandexToken").apply()
    }

    override fun apiKeyAuth() {
        prefs.edit().putString(PreferenceKeys.AUTH_KEY, "Bearer ${ApiKey.MY_KEY}").apply()
    }

    override fun getToken(): String {
        return prefs.getString(PreferenceKeys.AUTH_KEY, "Bearer ${ApiKey.MY_KEY}")
            ?: "Bearer ${ApiKey.MY_KEY}"
    }

    override fun canLogin(): Boolean {
        return prefs.contains(PreferenceKeys.AUTH_KEY)
    }
}
