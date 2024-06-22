package com.cleverpumpkin.todoapp.domain.models

import java.time.LocalDateTime

data class TodoItem(
    val id: String,
    var text: String,
    var importance: Importance,
    var deadline: LocalDateTime? = null,
    var isDone: Boolean,
    val createdAt: LocalDateTime,
    var modifiedAt: LocalDateTime? = null
)