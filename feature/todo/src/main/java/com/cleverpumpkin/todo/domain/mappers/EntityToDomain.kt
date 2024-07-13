package com.cleverpumpkin.todo.domain.mappers

import com.cleverpumpkin.database.data.TodoEntity
import com.cleverpumpkin.todo.domain.todo_model.Importance.Low
import com.cleverpumpkin.todo.domain.todo_model.Importance.Normal
import com.cleverpumpkin.todo.domain.todo_model.Importance.Urgent
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import java.time.LocalDateTime
import java.time.ZoneOffset

fun TodoEntity.toDomain(): TodoItem {
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
        deadline = this.deadline?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) },
        isDone = this.isDone,
        createdAt = LocalDateTime.ofEpochSecond(this.createdAt, 0, ZoneOffset.UTC),
        modifiedAt = LocalDateTime.ofEpochSecond(this.modifiedAt, 0, ZoneOffset.UTC)
    )
}