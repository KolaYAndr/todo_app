package com.cleverpumpkin.todoapp.presentation.view_model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.cleverpumpkin.core.data.preference.PreferenceKeys
import com.cleverpumpkin.core.domain.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val preferences: SharedPreferences) :
    ViewModel() {
        fun getThemeString() =
            preferences.getString(PreferenceKeys.THEME, AppTheme.System.type)
}