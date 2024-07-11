package com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.todoapp.domain.connectivity_observer.ConnectivityObserver
import com.cleverpumpkin.todoapp.domain.connectivity_observer.Status
import com.cleverpumpkin.todoapp.domain.models.Response
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepository
import com.cleverpumpkin.todoapp.domain.use_case.BackgroundSyncUseCase
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
    private val backgroundSyncUseCase: BackgroundSyncUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        getTodos()
        scheduleSync()
        observeNetworkState()
    }

    private fun observeNetworkState() {
        viewModelScope.launch(Dispatchers.IO) {
            connectivityObserver.observe().collectLatest { status ->
                processConnectivity(status)
            }
        }
    }

    private fun scheduleSync() {
        backgroundSyncUseCase.sync()
    }

    private fun getTodos() = viewModelScope.launch {
        processResponse(repository.fetchTodoItems()) {
            viewModelScope.launch {
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
        }

    }

    fun refresh() = viewModelScope.launch {
        processResponse(
            repository.fetchTodoItems()
        )
    }

    fun filter() = viewModelScope.launch {
        _uiState.update { it.copy(isFiltered = !it.isFiltered) }
    }

    fun checkItem(todo: TodoItem) = viewModelScope.launch {
        processResponse(
            repository.updateTodoItem(todo.copy(isDone = !todo.isDone))
        )
    }

    fun deleteItem(itemId: String) = viewModelScope.launch {
        processResponse(
            repository.deleteTodoItem(itemId)
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
                getTodos()
            }
            else -> Unit
        }
    }
}
