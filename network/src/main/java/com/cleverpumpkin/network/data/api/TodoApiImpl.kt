package com.cleverpumpkin.network.data.api

import com.cleverpumpkin.network.data.utils.ApiHeaders
import com.cleverpumpkin.network.data.utils.ApiRoutes
import com.cleverpumpkin.network.data.utils.executeRequest
import com.cleverpumpkin.network.domain.api.TodoApi
import com.cleverpumpkin.network.domain.dto.TodoItemDto
import com.cleverpumpkin.network.domain.requests.AddItemRequest
import com.cleverpumpkin.network.domain.requests.ChangeItemRequest
import com.cleverpumpkin.network.domain.requests.UpdateItemListRequest
import com.cleverpumpkin.network.domain.response_wrapper.Response
import com.cleverpumpkin.network.domain.responses.AddItemResponse
import com.cleverpumpkin.network.domain.responses.ChangeItemResponse
import com.cleverpumpkin.network.domain.responses.DeleteItemByIdResponse
import com.cleverpumpkin.network.domain.responses.GetItemByIdResponse
import com.cleverpumpkin.network.domain.responses.GetItemListResponse
import com.cleverpumpkin.network.domain.responses.UpdateItemListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import javax.inject.Inject

/**
 * Implementation of the TodoApi interface for performing CRUD operations on todo items via HTTP requests.
 */

class TodoApiImpl @Inject constructor(private val client: HttpClient) : TodoApi {

    override suspend fun getItems(
        revision: String,
        token: String
    ): Response<GetItemListResponse> {
        return client.executeRequest {
            get {
                url(ApiRoutes.getItemsListRoute())
                header(ApiHeaders.LAST_KNOWN_REVISION, revision)
                header(HttpHeaders.Authorization, token)
            }
        }
    }

    override suspend fun updateItemList(
        newList: List<TodoItemDto>,
        revision: String,
        token: String
    ): Response<UpdateItemListResponse> {
        val request = UpdateItemListRequest(items = newList)
        return client.executeRequest {
            patch {
                url(ApiRoutes.updateItemListRoute())
                setBody(request)
                header(ApiHeaders.LAST_KNOWN_REVISION, revision)
                header(HttpHeaders.Authorization, token)
            }
        }
    }

    override suspend fun getItemById(
        id: String,
        revision: String,
        token: String
    ): Response<GetItemByIdResponse> =
        client.executeRequest {
            get {
                url(ApiRoutes.getItemByIdRoute(id))
                header(ApiHeaders.LAST_KNOWN_REVISION, revision)
                header(HttpHeaders.Authorization, token)
            }
        }

    override suspend fun addItem(
        item: TodoItemDto,
        revision: String,
        token: String
    ): Response<AddItemResponse> {
        val request = AddItemRequest(item = item)
        return client.executeRequest {
            post {
                url(ApiRoutes.addItemRoute())
                setBody(request)
                header(ApiHeaders.LAST_KNOWN_REVISION, revision)
                header(HttpHeaders.Authorization, token)
            }
        }
    }

    override suspend fun changeItem(
        item: TodoItemDto,
        revision: String,
        token: String
    ): Response<ChangeItemResponse> {
        val request = ChangeItemRequest(item = item)
        return client.executeRequest {
            put {
                url(ApiRoutes.changeItemRoute(item.id))
                setBody(request)
                header(ApiHeaders.LAST_KNOWN_REVISION, revision)
                header(HttpHeaders.Authorization, token)
            }
        }
    }

    override suspend fun deleteItemById(
        id: String,
        revision: String,
        token: String
    ): Response<DeleteItemByIdResponse> = client.executeRequest {
        delete {
            url(ApiRoutes.deleteItemByIdRoute(id))
            header(ApiHeaders.LAST_KNOWN_REVISION, revision)
            header(HttpHeaders.Authorization, token)
        }
    }
}
