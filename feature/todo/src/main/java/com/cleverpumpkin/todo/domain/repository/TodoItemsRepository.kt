package com.cleverpumpkin.todo.domain.repository

import com.cleverpumpkin.networ.domain.response_wrapper.Response
import com.cleverpumpkin.networ.domain.responses.AddItemResponse
import com.cleverpumpkin.networ.domain.responses.ChangeItemResponse
import com.cleverpumpkin.networ.domain.responses.DeleteItemByIdResponse
import com.cleverpumpkin.networ.domain.responses.GetItemByIdResponse
import com.cleverpumpkin.networ.domain.responses.GetItemListResponse
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemsRepository {
    val todoItemsFlow: StateFlow<List<TodoItem>>

    suspend fun fetchTodoItems(): Response<GetItemListResponse>

    suspend fun addTodoItem(item: TodoItem): Response<AddItemResponse>

    suspend fun deleteTodoItem(itemId: String): Response<DeleteItemByIdResponse>

    suspend fun findItemById(id: String): Response<GetItemByIdResponse>

    suspend fun updateTodoItem(item: TodoItem): Response<ChangeItemResponse>
}
