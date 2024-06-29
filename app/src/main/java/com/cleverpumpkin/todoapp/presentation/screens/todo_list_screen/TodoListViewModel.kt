package com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val repository: TodoItemsRepo) : ViewModel() {
    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        getTodos()
    }

    private fun getTodos() = viewModelScope.launch {
        repository.fetchTodoItems()
            .catch { e -> _uiState.update { _uiState.value.copy(errorMessage = e.message) } }
            .collectLatest { todos ->
                _uiState.update {
                    TodoListUiState(
                        items = todos,
                        isFiltered = false,
                        completed = todos.count { it.isDone },
                        errorMessage = null
                    )
                }
            }
    }

    fun filter() = viewModelScope.launch {
        repository.fetchTodoItems()
            .catch { e -> _uiState.update { _uiState.value.copy(errorMessage = e.message) } }
            .collectLatest { todos ->
                if (!_uiState.value.isFiltered) {
                    _uiState.update {
                        _uiState.value.copy(
                            items = todos.filter { !it.isDone },
                            isFiltered = true
                        )
                    }
                } else {
                    _uiState.update { _uiState.value.copy(items = todos, isFiltered = false) }
                }

            }
    }

    fun deleteItem(item: TodoItem) = viewModelScope.launch {
        repository.deleteTodoItem(item)
    }
}