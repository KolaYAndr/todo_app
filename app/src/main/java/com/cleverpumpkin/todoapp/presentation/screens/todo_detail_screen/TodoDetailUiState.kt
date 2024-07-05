package com.cleverpumpkin.todoapp.presentation.screens.todo_detail_screen

import com.cleverpumpkin.todoapp.domain.models.Importance
import java.time.LocalDateTime

data class TodoDetailUiState(
    val id: String,
    val text: String,
    val importance: Importance,
    val createdAt: LocalDateTime,
    val deadline: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
    val isDone: Boolean,
    val errorMessage: String? = null
)