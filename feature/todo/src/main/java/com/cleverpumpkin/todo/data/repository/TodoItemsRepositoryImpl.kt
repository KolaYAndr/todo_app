package com.cleverpumpkin.todo.data.repository

import android.content.SharedPreferences
import com.cleverpumpkin.cor.data.preference.PreferenceKeys
import com.cleverpumpkin.networ.domain.api.TodoApi
import com.cleverpumpkin.networ.domain.response_wrapper.Response
import com.cleverpumpkin.networ.domain.responses.AddItemResponse
import com.cleverpumpkin.networ.domain.responses.ChangeItemResponse
import com.cleverpumpkin.networ.domain.responses.DeleteItemByIdResponse
import com.cleverpumpkin.networ.domain.responses.GetItemByIdResponse
import com.cleverpumpkin.networ.domain.responses.GetItemListResponse
import com.cleverpumpkin.todo.domain.mapper.toDomain
import com.cleverpumpkin.todo.domain.mapper.toDto
import com.cleverpumpkin.todo.domain.repository.TodoItemsRepository
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of the TodoItemsRepository interface that interacts with the API and local storage.
 */

class TodoItemsRepositoryImpl @Inject constructor(
    private val api: TodoApi,
    private val preferences: SharedPreferences,
    private val deviceIdProvider: com.cleverpumpkin.cor.id_handlers.DeviceIdProvider
) : TodoItemsRepository {

    private val _todoItemsFlow = MutableStateFlow<List<TodoItem>>(emptyList())
    override val todoItemsFlow: StateFlow<List<TodoItem>> get() = _todoItemsFlow.asStateFlow()

    override suspend fun fetchTodoItems(): Response<GetItemListResponse> =
        withContext(Dispatchers.IO) {
            val revision = preferences.getInt(PreferenceKeys.REVISION, 0).toString()
            val token = preferences.getString(PreferenceKeys.AUTH_KEY, "")!!
            val response = api.getItems(revision, token)
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
                _todoItemsFlow.update { response.result.items.map { it.toDomain() } }
            }
            response
        }

    override suspend fun addTodoItem(item: TodoItem): Response<AddItemResponse> =
        withContext(Dispatchers.IO) {
            val revision = preferences.getInt(PreferenceKeys.REVISION, 0).toString()
            val token = preferences.getString(PreferenceKeys.AUTH_KEY, "")!!
            val response =
                api.addItem(item = item.toDto(deviceIdProvider.provideDeviceId()), revision, token)
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
            fetchTodoItems()
            response
        }


    override suspend fun deleteTodoItem(itemId: String): Response<DeleteItemByIdResponse> =
        withContext(Dispatchers.IO) {
            val revision = preferences.getInt(PreferenceKeys.REVISION, 0).toString()
            val token = preferences.getString(PreferenceKeys.AUTH_KEY, "")!!
            val response = api.deleteItemById(itemId, revision, token)
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
            fetchTodoItems()
            response
        }


    override suspend fun findItemById(id: String): Response<GetItemByIdResponse> =
        withContext(Dispatchers.IO) {
            val revision = preferences.getInt(PreferenceKeys.REVISION, 0).toString()
            val token = preferences.getString(PreferenceKeys.AUTH_KEY, "")!!
            val response = api.getItemById(id, revision, token)
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
            fetchTodoItems()
            response
        }

    override suspend fun updateTodoItem(item: TodoItem): Response<ChangeItemResponse> =
        withContext(Dispatchers.IO) {
            val revision = preferences.getInt(PreferenceKeys.REVISION, 0).toString()
            val token = preferences.getString(PreferenceKeys.AUTH_KEY, "")!!
            val response = api.changeItem(item.toDto(deviceIdProvider.provideDeviceId()), revision, token)
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
            fetchTodoItems()
            response
        }


    private fun rewriteRevision(revision: Int) {
        preferences.edit().putInt(
            PreferenceKeys.REVISION,
            revision
        ).apply()
    }
}
