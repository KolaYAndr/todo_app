package com.cleverpumpkin.todoapp.domain.repository

import com.cleverpumpkin.todoapp.data.remote.responses.AddItemResponse
import com.cleverpumpkin.todoapp.data.remote.responses.ChangeItemResponse
import com.cleverpumpkin.todoapp.data.remote.responses.DeleteItemByIdResponse
import com.cleverpumpkin.todoapp.data.remote.responses.GetItemByIdResponse
import com.cleverpumpkin.todoapp.data.remote.responses.GetItemListResponse
import com.cleverpumpkin.todoapp.domain.models.Response
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemsRepository {
    val todoItemsFlow: StateFlow<List<TodoItem>>

    suspend fun fetchTodoItems(): Response<GetItemListResponse>

    suspend fun addTodoItem(item: TodoItem): Response<AddItemResponse>

    suspend fun deleteTodoItem(itemId: String): Response<DeleteItemByIdResponse>

    suspend fun findItemById(id: String): Response<GetItemByIdResponse>

    suspend fun updateTodoItem(item: TodoItem): Response<ChangeItemResponse>
}
