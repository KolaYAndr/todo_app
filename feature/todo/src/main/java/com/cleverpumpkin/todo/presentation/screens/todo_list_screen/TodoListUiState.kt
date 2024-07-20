package com.cleverpumpkin.todo.presentation.screens.todo_list_screen

/**
 * Data class representing the UI state of the todo list.
 */
data class TodoListUiState(
    val items: List<com.cleverpumpkin.todo.domain.todo_model.TodoItem> = emptyList(),
    val completed: Int = 0,
    val isFiltered: Boolean = false,
    val errorCode: Int? = null
)