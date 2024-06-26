package com.cleverpumpkin.todoapp.presentation.fragments.todo_detail_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(private val repository: TodoItemsRepo) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoDetailState>(TodoDetailState.Loading)
    val uiState: StateFlow<TodoDetailState> get() = _uiState.asStateFlow()

    fun findItem(id: String) {
        viewModelScope.launch {
            try {
                val item = repository.findItemById(id)
                _uiState.update {
                    TodoDetailState.Item(
                        id = item.id,
                        text = item.text,
                        importance = item.importance,
                        deadline = item.deadline,
                        modifiedAt = item.modifiedAt,
                        createdAt = item.createdAt,
                        isDone = item.isDone
                    )
                }
            } catch (e: Exception) {
                _uiState.update { TodoDetailState.Error(e.message) }
            }
        }
    }

    fun addItem(item: TodoItem) = repository.addTodoItem(item)

    fun deleteItem(item: TodoItem) = repository.deleteTodoItem(item)
}