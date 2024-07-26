package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.core.id_handlers.IdGenerator
import com.cleverpumpkin.core.presentation.navigation.NavArgs
import com.cleverpumpkin.network.domain.response_wrapper.Response
import com.cleverpumpkin.todo.domain.todo_model.Importance
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * ViewModel for managing the state and interactions of a detailed view of a todo item.
 */

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: com.cleverpumpkin.todo.domain.repository.TodoItemsRepository,
    private val idGenerator: IdGenerator
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(
        TodoDetailUiState(
            id = "",
            text = "",
            importance = Importance.Low,
            createdAt = LocalDateTime.now(),
            isDone = false
        )
    )
    val uiState: StateFlow<TodoDetailUiState> = _uiState.asStateFlow()

    fun findItem(id: String) = viewModelScope.launch {
        if (id != NavArgs.CREATE_TODO) {
            val item = repository.findItemById(id)
            _uiState.update {
                TodoDetailUiState(
                    id = item.id,
                    text = item.text,
                    createdAt = item.createdAt,
                    modifiedAt = item.modifiedAt,
                    deadline = item.deadline,
                    importance = item.importance,
                    isDone = item.isDone
                )
            }
        }
    }

    fun refresh() {
        findItem(_uiState.value.id)
    }

    private fun createTodo(): TodoItem {
        with(_uiState.value) {
            val todoId = if (id == "") idGenerator.generateId() else id
            return TodoItem(
                id = todoId,
                importance = importance,
                text = text,
                deadline = deadline,
                isDone = isDone,
                createdAt = createdAt,
                modifiedAt = LocalDateTime.now()
            )
        }
    }

    fun selectImportance(selectedImportance: Importance) {
        _uiState.update { it.copy(importance = selectedImportance) }
    }

    fun selectDeadline(selectedDeadline: LocalDateTime?) {
        _uiState.update { it.copy(deadline = selectedDeadline) }
    }

    fun changeText(newText: String) {
        _uiState.update { it.copy(text = newText) }
    }

    fun saveItem() = viewModelScope.launch {
        processResponse(
            if (_uiState.value.id == "") repository.addTodoItem(createTodo())
            else repository.updateTodoItem(createTodo())
        )

    }

    fun deleteItem() = viewModelScope.launch {
        if (_uiState.value.id.isNotEmpty()) {
            processResponse(repository.deleteTodoItem(createTodo()))
        }
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
}