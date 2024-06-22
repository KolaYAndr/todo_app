package com.cleverpumpkin.todoapp.presentation.fragments.todo_list_fragment

import androidx.lifecycle.ViewModel
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val repository: TodoItemsRepo) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoListState>(TodoListState.Loading)
    val uiState get() = _uiState.asStateFlow()

    init {
        getTodos()
    }

    private fun getTodos(){
        try {
            val todos = repository.getAllItems()
            _uiState.update { TodoListState.Todos(todos) }
        } catch (e: Exception) {
            _uiState.update { TodoListState.Error(e.message) }
        }
    }

    fun deleteItem(item: TodoItem) {
        repository.deleteTodoItem(item)
    }
}