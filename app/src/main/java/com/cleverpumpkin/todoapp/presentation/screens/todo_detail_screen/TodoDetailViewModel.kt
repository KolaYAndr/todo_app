package com.cleverpumpkin.todoapp.presentation.screens.todo_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.todoapp.data.mapper.toDomain
import com.cleverpumpkin.todoapp.domain.id_handlers.IdGenerator
import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.Response
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepository
import com.cleverpumpkin.todoapp.presentation.navigation.NavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: TodoItemsRepository,
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
        try {
            if (id != NavArgs.CREATE_TODO) {
                when (val response = repository.findItemById(id)) {
                    is Response.Failure -> _uiState.update {
                        TodoDetailUiState(
                            errorMessage = response.exception.message, id = "",
                            text = "",
                            importance = Importance.Low,
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
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message) }
        }
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
        if (_uiState.value.id == "") repository.addTodoItem(createTodo())
        else repository.updateTodoItem(createTodo())
    }

    fun deleteItem() = viewModelScope.launch {
        repository.deleteTodoItem(_uiState.value.id)
    }
}
