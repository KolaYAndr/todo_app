package com.cleverpumpkin.todoapp.data.remote.responses

import com.cleverpumpkin.todoapp.data.remote.dto.TodoItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetItemListResponse (
    @SerialName("status")
    val status: String,

    @SerialName("list")
    val items: List<TodoItemDto>,

    @SerialName("revision")
    val revision: Int
)
