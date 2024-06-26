package com.cleverpumpkin.todoapp.presentation.fragments.todo_detail_fragment

import com.cleverpumpkin.todoapp.domain.models.Importance
import java.time.LocalDateTime

sealed class TodoDetailState {
    data class Item(
        val id: String,
        val text: String,
        val importance: Importance,
        val createdAt: LocalDateTime,
        val deadline: LocalDateTime?,
        val modifiedAt: LocalDateTime?,
        val isDone: Boolean
    ) : TodoDetailState()

    data class Error(val message: String?) : TodoDetailState()
    data object Loading : TodoDetailState()
}