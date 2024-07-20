package com.cleverpumpkin.network.domain.requests

import com.cleverpumpkin.network.domain.dto.TodoItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangeItemRequest (
    @SerialName("status")
    val status: String = "ok",

    @SerialName("element")
    val item: TodoItemDto
)
