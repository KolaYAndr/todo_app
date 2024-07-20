package com.cleverpumpkin.networ.domain.requests

import com.cleverpumpkin.networ.domain.dto.TodoItemDto
import kotlinx.serialization.SerialName

data class UpdateItemListRequest(
    @SerialName("status")
    val status: String = "ok",

    @SerialName("list")
    val items: List<TodoItemDto>
)
