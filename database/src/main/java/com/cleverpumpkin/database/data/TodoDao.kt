package com.cleverpumpkin.database.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TodoDao {
    @Query("SELECT * FROM todoItems")
    suspend fun getAll(): List<TodoEntity>

    @Delete
    suspend fun delete(todo: TodoEntity)

    @Upsert
    suspend fun upsert(todo: TodoEntity)

    @Upsert
    suspend fun upsert(todos: List<TodoEntity>)

    @Query("SELECT * FROM todoItems WHERE id = :id")
    fun findItem(id: String): TodoEntity
}