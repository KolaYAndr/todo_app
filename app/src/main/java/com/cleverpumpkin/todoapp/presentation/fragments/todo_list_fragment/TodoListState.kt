package com.cleverpumpkin.todoapp.presentation.fragments.todo_list_fragment

import com.cleverpumpkin.todoapp.domain.models.TodoItem

sealed class TodoListState {
    data class Todos(val items: List<TodoItem>) : TodoListState()
    data class Error(val message: String?) : TodoListState()
    data object Loading : TodoListState()
}