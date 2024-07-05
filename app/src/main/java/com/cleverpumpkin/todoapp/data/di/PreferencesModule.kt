package com.cleverpumpkin.todoapp.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreferencesModule {
    companion object {
        private const val PREFERENCES_NAME = "prefs"

        @Provides
        @Singleton
        fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_NAME, 0)
        }
    }
}