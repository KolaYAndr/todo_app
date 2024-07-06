package com.cleverpumpkin.todoapp.data.di

import com.cleverpumpkin.todoapp.data.repository.AuthRepositoryImpl
import com.cleverpumpkin.todoapp.data.repository.TodoItemsRepositoryImpl
import com.cleverpumpkin.todoapp.domain.repository.AuthRepository
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindTodoRepository(impl: TodoItemsRepositoryImpl): TodoItemsRepository

    @Binds
    @Singleton
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
