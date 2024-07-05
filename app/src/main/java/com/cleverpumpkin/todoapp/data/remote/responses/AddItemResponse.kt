package com.cleverpumpkin.todoapp.data.remote.responses

import com.cleverpumpkin.todoapp.data.remote.dto.TodoItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddItemResponse(
    @SerialName("status")
    val status: String,

    @SerialName("element")
    val item: TodoItemDto,

    @SerialName("revision")
    val revision: Int
)