package com.cleverpumpkin.network.domain.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemDto(
    @SerialName("id")
    val id: String,

    @SerialName("text")
    val description: String,

    @SerialName("importance")
    val importance: String,

    @SerialName("deadline")
    val deadline: Long? = null,

    @SerialName("done")
    val isDone: Boolean,

    @SerialName("color")
    val color: String? = null,

    @SerialName("files")
    val files: String? = null,

    @SerialName("created_at")
    val createdAt: Long,

    @SerialName("changed_at")
    val modifiedAt: Long,

    @SerialName("last_updated_by")
    val lastUpdatedBy: String
)
