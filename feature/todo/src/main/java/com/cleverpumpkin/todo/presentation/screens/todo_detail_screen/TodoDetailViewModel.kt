package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.cor.id_handlers.IdGenerator
import com.cleverpumpkin.cor.presentation.navigation.NavArgs
import com.cleverpumpkin.networ.domain.response_wrapper.Response
import com.cleverpumpkin.todo.domain.mapper.toDomain
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
            importance = com.cleverpumpkin.todo.domain.todo_model.Importance.Low,
            createdAt = LocalDateTime.now(),
            isDone = false
        )
    )
    val uiState: StateFlow<TodoDetailUiState> = _uiState.asStateFlow()

    fun findItem(id: String) = viewModelScope.launch {
        if (id != NavArgs.CREATE_TODO) {
            when (val response = repository.findItemById(id)) {
                is Response.Failure -> _uiState.update {
                    TodoDetailUiState(
                        errorCode = response.exceptionCode,
                        id = "",
                        text = "",
                        importance = com.cleverpumpkin.todo.domain.todo_model.Importance.Low,
                        createdAt = LocalDateTime.now(),
                        isDone = false
                    )
                }

                is Response.Success -> {
                    val item = response.result.item.toDomain()
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

        }
    }

    fun refresh() {
        findItem(_uiState.value.id)
    }

    private fun createTodo(): com.cleverpumpkin.todo.domain.todo_model.TodoItem {
        with(_uiState.value) {
            val todoId = if (id == "") idGenerator.generateId() else id
            return com.cleverpumpkin.todo.domain.todo_model.TodoItem(
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

    fun selectImportance(selectedImportance: com.cleverpumpkin.todo.domain.todo_model.Importance) {
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
        processResponse(repository.deleteTodoItem(_uiState.value.id))
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