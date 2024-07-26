package com.cleverpumpkin.todo.presentation.screens.todo_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.network.domain.connectivity_observer.ConnectivityObserver
import com.cleverpumpkin.network.domain.connectivity_observer.Status
import com.cleverpumpkin.network.domain.response_wrapper.Response
import com.cleverpumpkin.todo.domain.repository.TodoItemsRepository
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing the state and interactions of a todo list
 */

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoItemsRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        getTodos()
        observeNetworkState()
    }

    private fun observeNetworkState() {
        viewModelScope.launch(Dispatchers.IO) {
            connectivityObserver.observe().collectLatest { status ->
                processConnectivity(status)
            }
        }
    }

    private fun getTodos() = viewModelScope.launch {
        repository.fetchTodoItems()
        viewModelScope.launch {
            repository.todoItemsFlow.combine(_uiState) { items: List<TodoItem>, state: TodoListUiState ->
                val filteredItems =
                    if (state.isFiltered) items.filter { item -> !item.isDone } else items
                filteredItems
            }.collectLatest { filteredItems ->
                val count = filteredItems.count { it.isDone }
                _uiState.update { it.copy(items = filteredItems, completed = count) }
            }
        }
    }

    fun refresh() = viewModelScope.launch {
        _uiState.update { it.copy(errorCode = null) }
        getTodos()
    }

    fun filter() = viewModelScope.launch {
        _uiState.update { it.copy(isFiltered = !it.isFiltered) }
    }

    fun checkItem(todo: TodoItem) = viewModelScope.launch {
        processResponse(
            repository.updateTodoItem(todo.copy(isDone = !todo.isDone))
        )
    }

    fun deleteItem(item: TodoItem) = viewModelScope.launch {
        processResponse(
            repository.deleteTodoItem(item)
        )
    }

    private fun processResponse(response: Response<*>, onSuccessAction: (() -> Unit)? = null) {
        when (response) {
            is Response.Failure -> {
                _uiState.update { it.copy(errorCode = response.exceptionCode) }
                refresh()
            }

            is Response.Success -> onSuccessAction?.invoke()
        }
    }

    private fun processConnectivity(status: Status) {
        when (status) {
            Status.Available -> if (connectivityObserver.wasPreviouslyOffline()) {
                refresh()
                viewModelScope.launch {
                    repository.uploadTodoItems()
                }
            }

            else -> Unit
        }
    }
}
