package com.cleverpumpkin.settings.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.cleverpumpkin.core.data.preference.PreferenceKeys
import com.cleverpumpkin.core.domain.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val preferences: SharedPreferences) :
    ViewModel() {
    fun setTheme(theme: AppTheme) {
        preferences.edit().putString(PreferenceKeys.THEME, theme.type).apply()
    }

    fun getThemeString(): String {
        return preferences.getString(PreferenceKeys.THEME, AppTheme.System.type) ?: AppTheme.System.type
    }
}