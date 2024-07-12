package com.cleverpumpkin.todo.domain.mapper

import com.cleverpumpkin.networ.domain.dto.TodoItemDto
import com.cleverpumpkin.todo.domain.todo_model.Importance.*
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import java.time.LocalDateTime
import java.time.ZoneOffset

fun TodoItemDto.toDomain(): TodoItem {
    val importance = when (this.importance) {
        "low" -> Low
        "basic" -> Normal
        "important" -> Urgent
        else -> Low
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
