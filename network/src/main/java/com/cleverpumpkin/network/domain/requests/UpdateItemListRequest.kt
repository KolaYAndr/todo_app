package com.cleverpumpkin.network.domain.requests

import com.cleverpumpkin.network.domain.dto.TodoItemDto
import kotlinx.serialization.SerialName

data class UpdateItemListRequest(
    @SerialName("status")
    val status: String = "ok",

    @SerialName("list")
    val items: List<TodoItemDto>
)
