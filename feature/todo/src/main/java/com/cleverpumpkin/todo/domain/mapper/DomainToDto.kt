package com.cleverpumpkin.todo.domain.mapper

import com.cleverpumpkin.networ.domain.dto.TodoItemDto
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import java.time.ZoneOffset

fun TodoItem.toDto(lastUpdatedBy: String): TodoItemDto {
    val importanceString = when (this.importance) {
        com.cleverpumpkin.todo.domain.todo_model.Importance.Low -> "low"
        com.cleverpumpkin.todo.domain.todo_model.Importance.Normal -> "basic"
        com.cleverpumpkin.todo.domain.todo_model.Importance.Urgent -> "important"
    }

    return TodoItemDto(
        id = this.id,
        description = this.text,
        importance = importanceString,
        deadline = this.deadline?.toEpochSecond(ZoneOffset.UTC),
        isDone = this.isDone,
        createdAt = this.createdAt.toEpochSecond(ZoneOffset.UTC),
        modifiedAt = this.modifiedAt?.toEpochSecond(ZoneOffset.UTC) ?: this.createdAt.toEpochSecond(
            ZoneOffset.UTC
        ),
        lastUpdatedBy = lastUpdatedBy
    )
}
