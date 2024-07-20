package com.cleverpumpkin.todo.domain.repository

import com.cleverpumpkin.network.domain.response_wrapper.Response
import com.cleverpumpkin.network.domain.responses.AddItemResponse
import com.cleverpumpkin.network.domain.responses.ChangeItemResponse
import com.cleverpumpkin.network.domain.responses.DeleteItemByIdResponse
import com.cleverpumpkin.network.domain.responses.GetItemListResponse
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemsRepository {
    val todoItemsFlow: StateFlow<List<TodoItem>>

    suspend fun fetchTodoItems(): Response<GetItemListResponse>

    suspend fun addTodoItem(item: TodoItem): Response<AddItemResponse>

    suspend fun deleteTodoItem(item: TodoItem): Response<DeleteItemByIdResponse>

    suspend fun findItemById(id: String): TodoItem

    suspend fun updateTodoItem(item: TodoItem): Response<ChangeItemResponse>

    suspend fun uploadTodoItems()
}
