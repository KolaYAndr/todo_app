package com.cleverpumpkin.settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun putThemeStrings(key:String, value:String)
    suspend fun getThemeStrings(key: String): Flow<String?>
}