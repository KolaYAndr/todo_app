package com.cleverpumpkin.todo.presentation.screens.todo_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.networ.domain.connectivity_observer.ConnectivityObserver
import com.cleverpumpkin.networ.domain.connectivity_observer.Status
import com.cleverpumpkin.networ.domain.response_wrapper.Response
import com.cleverpumpkin.todo.data.task_scheduler.BackgroundTaskScheduler
import com.cleverpumpkin.todo.domain.repository.TodoItemsRepository
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
    private val backgroundTaskScheduler: BackgroundTaskScheduler,
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
        backgroundTaskScheduler.sync()
    }

    private fun getTodos() = viewModelScope.launch {
        processResponse(repository.fetchTodoItems()) {
            viewModelScope.launch {
                repository.todoItemsFlow.combine(_uiState) { items: List<com.cleverpumpkin.todo.domain.todo_model.TodoItem>, state: TodoListUiState ->
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
        _uiState.update { it.copy(errorCode = null) }
        getTodos()
    }

    fun filter() = viewModelScope.launch {
        _uiState.update { it.copy(isFiltered = !it.isFiltered) }
    }

    fun checkItem(todo: com.cleverpumpkin.todo.domain.todo_model.TodoItem) = viewModelScope.launch {
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
                refresh()
            }
            else -> Unit
        }
    }
}
