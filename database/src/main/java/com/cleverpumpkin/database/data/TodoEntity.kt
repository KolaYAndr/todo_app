package com.cleverpumpkin.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoItems")
data class TodoEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("importance")
    val importance: String,

    @ColumnInfo("is_done")
    val isDone: Boolean,

    @ColumnInfo("is_deleted")
    val isDeleted:Boolean,

    @ColumnInfo("created_at")
    val createdAt: Long,

    @ColumnInfo("modified_at")
    val modifiedAt: Long,

    @ColumnInfo("deadline")
    val deadline: Long?,

    @ColumnInfo("last_updated_by")
    val lastUpdatedBy: String
)