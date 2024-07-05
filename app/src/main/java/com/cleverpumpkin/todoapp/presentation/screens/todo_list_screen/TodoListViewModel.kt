package com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepository
import com.cleverpumpkin.todoapp.domain.use_case.BackgroundSyncUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoItemsRepository,
    private val backgroundSyncUseCase: BackgroundSyncUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        getTodos()
        scheduleSync()
    }

    private fun scheduleSync() {
        backgroundSyncUseCase.sync()
    }

    private fun getTodos() = viewModelScope.launch {
        repository.fetchTodoItems()
        repository.todoItemsFlow.combine(_uiState) { items: List<TodoItem>, state: TodoListUiState ->
            val filteredItems =
                if (state.isFiltered) items.filter { item -> !item.isDone } else items
            filteredItems
        }.collectLatest { filteredItems ->
            val count = filteredItems.count { it.isDone }
            val completed = maxOf(_uiState.value.completed, count)
            _uiState.update { it.copy(items = filteredItems, completed = completed) }
        }
    }

    fun filter() = viewModelScope.launch {
        _uiState.update { it.copy(isFiltered = !it.isFiltered) }
    }

    fun checkItem(todo: TodoItem) = viewModelScope.launch {
        repository.updateTodoItem(todo.copy(isDone = !todo.isDone))
    }

    fun deleteItem(itemId: String) = viewModelScope.launch {
        repository.deleteTodoItem(itemId)
    }
}