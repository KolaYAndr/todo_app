package com.cleverpumpkin.network.domain.api

import com.cleverpumpkin.network.domain.dto.TodoItemDto
import com.cleverpumpkin.network.domain.response_wrapper.Response
import com.cleverpumpkin.network.domain.responses.AddItemResponse
import com.cleverpumpkin.network.domain.responses.ChangeItemResponse
import com.cleverpumpkin.network.domain.responses.DeleteItemByIdResponse
import com.cleverpumpkin.network.domain.responses.GetItemByIdResponse
import com.cleverpumpkin.network.domain.responses.GetItemListResponse
import com.cleverpumpkin.network.domain.responses.UpdateItemListResponse

interface TodoApi {
    suspend fun getItems(
        revision: String,
        token: String
    ): Response<GetItemListResponse>

    suspend fun updateItemList(
        newList: List<TodoItemDto>,
        revision: String,
        token: String
    ): Response<UpdateItemListResponse>

    suspend fun getItemById(
        id: String, revision: String,
        token: String
    ): Response<GetItemByIdResponse>

    suspend fun addItem(
        item: TodoItemDto, revision: String,
        token: String
    ): Response<AddItemResponse>

    suspend fun changeItem(
        item: TodoItemDto, revision: String,
        token: String
    ): Response<ChangeItemResponse>

    suspend fun deleteItemById(
        id: String, revision: String,
        token: String
    ): Response<DeleteItemByIdResponse>
}
