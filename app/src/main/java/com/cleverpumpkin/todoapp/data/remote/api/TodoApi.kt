package com.cleverpumpkin.todoapp.data.remote.api

import com.cleverpumpkin.todoapp.data.remote.dto.TodoItemDto
import com.cleverpumpkin.todoapp.data.remote.responses.AddItemResponse
import com.cleverpumpkin.todoapp.data.remote.responses.ChangeItemResponse
import com.cleverpumpkin.todoapp.data.remote.responses.DeleteItemByIdResponse
import com.cleverpumpkin.todoapp.data.remote.responses.GetItemByIdResponse
import com.cleverpumpkin.todoapp.data.remote.responses.GetItemListResponse
import com.cleverpumpkin.todoapp.data.remote.responses.UpdateItemListResponse
import com.cleverpumpkin.todoapp.domain.models.Response

interface TodoApi {
    suspend fun getItems(): Response<GetItemListResponse>

    suspend fun updateItemList(
        newList: List<TodoItemDto>,
        revision: String
    ): Response<UpdateItemListResponse>

    suspend fun getItemById(id: String): Response<GetItemByIdResponse>

    suspend fun addItem(item: TodoItemDto, revision: String): Response<AddItemResponse>

    suspend fun changeItem(item: TodoItemDto, revision: String): Response<ChangeItemResponse>

    suspend fun deleteItemById(id: String, revision: String): Response<DeleteItemByIdResponse>
}
