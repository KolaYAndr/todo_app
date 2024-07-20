package com.cleverpumpkin.todo.domain.mappers

import com.cleverpumpkin.database.data.TodoEntity
import com.cleverpumpkin.network.domain.dto.TodoItemDto

fun TodoItemDto.toEntity(): TodoEntity {
    return TodoEntity(
        id = this.id,
        description = this.description,
        importance = this.importance,
        isDone = this.isDone,
        isDeleted = false,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt,
        deadline = this.deadline,
        lastUpdatedBy = this.lastUpdatedBy
    )
}