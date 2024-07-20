package com.cleverpumpkin.networ.domain.requests

import com.cleverpumpkin.networ.domain.dto.TodoItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddItemRequest(
    @SerialName("status")
    val status: String = "ok",

    @SerialName("element")
    val item: TodoItemDto
)
