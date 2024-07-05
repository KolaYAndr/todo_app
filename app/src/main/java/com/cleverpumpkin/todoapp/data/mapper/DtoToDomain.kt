package com.cleverpumpkin.todoapp.data.mapper

import com.cleverpumpkin.todoapp.data.remote.dto.TodoItemDto
import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import java.time.LocalDateTime
import java.time.ZoneOffset

fun TodoItemDto.toDomain(): TodoItem {
    val importance = when (this.importance) {
        "low" -> Importance.Low
        "basic" -> Importance.Normal
        "important" -> Importance.Urgent
        else -> Importance.Low
    }
    return TodoItem(
        id = this.id,
        text = this.description,
        importance = importance,
        createdAt = LocalDateTime.ofEpochSecond(this.createdAt, 0, ZoneOffset.UTC),
        isDone = this.isDone,
        deadline = this.deadline?.let {LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)},
        modifiedAt = LocalDateTime.ofEpochSecond(this.modifiedAt, 0, ZoneOffset.UTC)
    )
}