package com.cleverpumpkin.networ.domain.api

import com.cleverpumpkin.networ.domain.dto.TodoItemDto
import com.cleverpumpkin.networ.domain.response_wrapper.Response
import com.cleverpumpkin.networ.domain.responses.AddItemResponse
import com.cleverpumpkin.networ.domain.responses.ChangeItemResponse
import com.cleverpumpkin.networ.domain.responses.DeleteItemByIdResponse
import com.cleverpumpkin.networ.domain.responses.GetItemByIdResponse
import com.cleverpumpkin.networ.domain.responses.GetItemListResponse
import com.cleverpumpkin.networ.domain.responses.UpdateItemListResponse

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
