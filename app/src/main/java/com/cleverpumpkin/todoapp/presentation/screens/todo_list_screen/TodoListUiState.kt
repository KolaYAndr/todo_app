package com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen

import com.cleverpumpkin.todoapp.domain.models.TodoItem

data class TodoListUiState(
    val items: List<TodoItem> = emptyList(),
    val completed: Int = 0,
    val isFiltered: Boolean = false,
    val errorMessage: String? = null
)
