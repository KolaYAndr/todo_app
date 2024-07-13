package com.cleverpumpkin.database.di

import android.content.Context
import com.cleverpumpkin.database.data.TodoDao
import com.cleverpumpkin.database.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {
    companion object {
        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context) : TodoDatabase {
            return TodoDatabase.getDatabase(context)
        }

        @Singleton
        @Provides
        fun provideDao(db: TodoDatabase): TodoDao{
            return db.todoDao()
        }
    }
}