package com.cleverpumpkin.todoapp.presentation.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
fun getThemeFlagByString(string: String?) =  when (string) {
    "Light" -> false
    "Dark" -> true
    else -> isSystemInDarkTheme()
}