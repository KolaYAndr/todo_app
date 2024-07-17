package com.cleverpumpkin.networ.domain.responses

import com.cleverpumpkin.networ.domain.dto.TodoItemDto
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
