package com.cleverpumpkin.core.domain

sealed class AppTheme(val type: String) {
    data object System: AppTheme("System")
    data object Dark: AppTheme("Dark")
    data object Light: AppTheme("Light")
}