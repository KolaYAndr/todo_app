package com.cleverpumpkin.todoapp.domain.repository

import com.cleverpumpkin.todoapp.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepo {
    suspend fun fetchTodoItems(): Flow<List<TodoItem>>

    suspend fun getCompletedNumber(): Int

    suspend fun addTodoItem(item: TodoItem)

    suspend fun deleteTodoItem(item: TodoItem)

    suspend fun findItemById(id: String) : TodoItem
}