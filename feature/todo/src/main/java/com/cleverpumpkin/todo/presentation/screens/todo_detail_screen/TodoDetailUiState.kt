package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen

import java.time.LocalDateTime

/**
 * Data class representing the UI state of a detailed view of a todo item.
 */

data class TodoDetailUiState(
    val id: String,
    val text: String,
    val importance: com.cleverpumpkin.todo.domain.todo_model.Importance,
    val createdAt: LocalDateTime,
    val deadline: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
    val isDone: Boolean,
    val errorCode: Int? = null
)
