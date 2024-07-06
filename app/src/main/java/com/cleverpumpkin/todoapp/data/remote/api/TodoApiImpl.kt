package com.cleverpumpkin.todoapp.data.remote.api

import com.cleverpumpkin.todoapp.data.remote.dto.TodoItemDto
import com.cleverpumpkin.todoapp.data.remote.requests.AddItemRequest
import com.cleverpumpkin.todoapp.data.remote.requests.ChangeItemRequest
import com.cleverpumpkin.todoapp.data.remote.requests.UpdateItemListRequest
import com.cleverpumpkin.todoapp.data.remote.utils.ApiRoutes
import com.cleverpumpkin.todoapp.data.remote.responses.AddItemResponse
import com.cleverpumpkin.todoapp.data.remote.responses.ChangeItemResponse
import com.cleverpumpkin.todoapp.data.remote.responses.DeleteItemByIdResponse
import com.cleverpumpkin.todoapp.data.remote.responses.GetItemByIdResponse
import com.cleverpumpkin.todoapp.data.remote.responses.GetItemListResponse
import com.cleverpumpkin.todoapp.data.remote.responses.UpdateItemListResponse
import com.cleverpumpkin.todoapp.data.remote.utils.ApiHeaders
import com.cleverpumpkin.todoapp.data.remote.utils.executeRequest
import com.cleverpumpkin.todoapp.domain.models.Response
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import javax.inject.Inject

/**
 * Implementation of the TodoApi interface for performing CRUD operations on todo items via HTTP requests.
 */

class TodoApiImpl @Inject constructor(private val client: HttpClient) : TodoApi {

    override suspend fun getItems(): Response<GetItemListResponse> {
        return client.executeRequest { get { url(ApiRoutes.getItemsListRoute()) } }
    }

    override suspend fun updateItemList(
        newList: List<TodoItemDto>,
        revision: String
    ): Response<UpdateItemListResponse> {
        val request = UpdateItemListRequest(items = newList)
        return client.executeRequest {
            patch {
                url(ApiRoutes.updateItemListRoute())
                setBody(request)
                header(ApiHeaders.LAST_KNOWN_REVISION, revision)
            }
        }
    }

    override suspend fun getItemById(id: String): Response<GetItemByIdResponse> =
        client.executeRequest { get { url(ApiRoutes.getItemByIdRoute(id)) } }

    override suspend fun addItem(item: TodoItemDto, revision: String): Response<AddItemResponse> {
        val request = AddItemRequest(item = item)
        return client.executeRequest {
            post {
                url(ApiRoutes.addItemRoute())
                setBody(request)
                header(ApiHeaders.LAST_KNOWN_REVISION, revision)
            }
        }
    }

    override suspend fun changeItem(
        item: TodoItemDto,
        revision: String
    ): Response<ChangeItemResponse> {
        val request = ChangeItemRequest(item = item)
        return client.executeRequest {
            put {
                url(ApiRoutes.changeItemRoute(item.id))
                setBody(request)
                header(ApiHeaders.LAST_KNOWN_REVISION, revision)
            }
        }
    }

    override suspend fun deleteItemById(
        id: String,
        revision: String
    ): Response<DeleteItemByIdResponse> = client.executeRequest {
        delete {
            url(ApiRoutes.deleteItemByIdRoute(id))
            header(ApiHeaders.LAST_KNOWN_REVISION, revision)
        }
    }
}
