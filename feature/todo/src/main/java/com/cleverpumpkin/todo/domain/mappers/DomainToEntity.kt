package com.cleverpumpkin.todo.domain.mappers

import com.cleverpumpkin.database.data.TodoEntity
import com.cleverpumpkin.todo.domain.todo_model.Importance
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import java.time.ZoneOffset

fun TodoItem.toEntity(lastUpdatedBy: String): TodoEntity {
    val importanceString = when (this.importance) {
        Importance.Low -> "low"
        Importance.Normal -> "basic"
        Importance.Urgent -> "important"
    }
    return TodoEntity(
        id = this.id,
        description = this.text,
        importance = importanceString,
        isDone = this.isDone,
        isDeleted = false,
        createdAt = this.createdAt.toEpochSecond(ZoneOffset.UTC),
        modifiedAt = this.modifiedAt?.toEpochSecond(ZoneOffset.UTC) ?:
                                        this.createdAt.toEpochSecond(ZoneOffset.UTC),
        deadline = this.deadline?.toEpochSecond(ZoneOffset.UTC),
        lastUpdatedBy = lastUpdatedBy
    )
}