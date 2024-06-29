package com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen

import com.cleverpumpkin.todoapp.domain.models.TodoItem

sealed class TodoListUiState {
    data class Todos(val items: List<TodoItem>, val isFiltered: Boolean, val completed: Int) : TodoListUiState()
    data class Error(val message: String?) : TodoListUiState()
    data object Loading : TodoListUiState()
}