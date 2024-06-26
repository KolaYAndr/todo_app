package com.cleverpumpkin.todoapp.data.di

import com.cleverpumpkin.todoapp.data.repository.TodoItemsRepository
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    companion object {
        @Provides
        @Singleton
        fun provideTodoRepository(): TodoItemsRepo = TodoItemsRepository()
    }
}