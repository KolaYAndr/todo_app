package com.cleverpumpkin.todoapp.presentation.screens.todo_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepo
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
class TodoDetailViewModel @Inject constructor(private val repository: TodoItemsRepo) : ViewModel() {
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
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message) }
        }
    }

    private fun createTodo(): TodoItem {
        with(_uiState.value) {
            return TodoItem(
                id = id,
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

    fun addItem() = viewModelScope.launch {
        repository.addTodoItem(createTodo())
    }

    fun deleteItem() = viewModelScope.launch {
        repository.deleteTodoItem(createTodo())
    }
}