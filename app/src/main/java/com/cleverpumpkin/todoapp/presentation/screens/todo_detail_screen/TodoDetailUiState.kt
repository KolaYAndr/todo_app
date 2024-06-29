package com.cleverpumpkin.todoapp.presentation.screens.todo_detail_screen

import com.cleverpumpkin.todoapp.domain.models.Importance
import java.time.LocalDateTime

sealed class TodoDetailUiState {
    data class Item(
        val id: String,
        var text: String,
        var importance: Importance,
        val createdAt: LocalDateTime,
        var deadline: LocalDateTime?,
        var modifiedAt: LocalDateTime?,
        var isDone: Boolean
    ) : TodoDetailUiState()

    data class Error(val message: String?) : TodoDetailUiState()
    data object Loading : TodoDetailUiState()
}