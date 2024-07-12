package com.cleverpumpkin.todo.domain.todo_model

import java.time.LocalDateTime

/**
 * Data class representing a todo item.
 */

data class TodoItem(
    val id: String,

    val text: String,

    val importance: Importance,

    val deadline: LocalDateTime? = null,

    val isDone: Boolean,

    val createdAt: LocalDateTime,

    val modifiedAt: LocalDateTime? = null
)
