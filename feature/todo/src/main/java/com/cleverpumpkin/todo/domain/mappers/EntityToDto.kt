package com.cleverpumpkin.todo.domain.mappers

import com.cleverpumpkin.database.data.TodoEntity
import com.cleverpumpkin.networ.domain.dto.TodoItemDto
fun TodoEntity.toDto(): TodoItemDto {
    return TodoItemDto(
        id = this.id,
        description = this.description,
        importance = this.importance,
        deadline = this.deadline,
        isDone = this.isDone,
        lastUpdatedBy = this.lastUpdatedBy,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt
    )
}