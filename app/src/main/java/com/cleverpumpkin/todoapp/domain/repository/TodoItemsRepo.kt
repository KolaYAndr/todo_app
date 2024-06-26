package com.cleverpumpkin.todoapp.domain.repository

import com.cleverpumpkin.todoapp.domain.models.TodoItem

interface TodoItemsRepo {
    fun getAllItems(): List<TodoItem>

    fun addTodoItem(item: TodoItem) : Boolean

    fun deleteTodoItem(item: TodoItem) : Boolean

    fun findItemById(id: String) : TodoItem
}