package com.cleverpumpkin.todoapp.data.remote.requests

import com.cleverpumpkin.todoapp.data.remote.dto.TodoItemDto
import kotlinx.serialization.SerialName

data class UpdateItemListRequest(
    @SerialName("status")
    val status: String = "ok",

    @SerialName("list")
    val items: List<TodoItemDto>
)
