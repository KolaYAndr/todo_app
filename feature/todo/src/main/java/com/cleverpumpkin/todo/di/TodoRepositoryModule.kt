package com.cleverpumpkin.todo.di

import com.cleverpumpkin.todo.data.repository.TodoItemsRepositoryImpl
import com.cleverpumpkin.todo.domain.repository.TodoItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TodoRepositoryModule {

    @Binds
    @Singleton
    fun bindTodoRepository(impl: TodoItemsRepositoryImpl): TodoItemsRepository
}