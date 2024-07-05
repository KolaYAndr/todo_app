package com.cleverpumpkin.todoapp.domain.mapper

import com.cleverpumpkin.todoapp.data.remote.dto.TodoItemDto
import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import java.time.ZoneOffset

fun TodoItem.toDto(lastUpdatedBy: String): TodoItemDto {
    val importanceString = when (this.importance) {
        Importance.Low -> "low"
        Importance.Normal -> "basic"
        Importance.Urgent -> "important"
    }

    return TodoItemDto(
        id = this.id,
        description = this.text,
        importance = importanceString,
        deadline = this.deadline?.toEpochSecond(ZoneOffset.UTC),
        isDone = this.isDone,
        createdAt = this.createdAt.toEpochSecond(ZoneOffset.UTC),
        modifiedAt = this.modifiedAt?.toEpochSecond(ZoneOffset.UTC) ?:
                                                    this.createdAt.toEpochSecond(ZoneOffset.UTC),
        lastUpdatedBy = lastUpdatedBy
    )
}
