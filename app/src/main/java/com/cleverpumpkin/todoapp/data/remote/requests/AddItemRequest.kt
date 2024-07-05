package com.cleverpumpkin.todoapp.data.remote.requests

import com.cleverpumpkin.todoapp.data.remote.dto.TodoItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddItemRequest(
    @SerialName("status")
    val status: String = "ok",

    @SerialName("element")
    val item: TodoItemDto
)