package com.cleverpumpkin.todoapp.data.repository

import android.content.SharedPreferences
import com.cleverpumpkin.todoapp.data.mapper.toDomain
import com.cleverpumpkin.todoapp.data.preferences.PreferenceKeys
import com.cleverpumpkin.todoapp.data.remote.api.TodoApi
import com.cleverpumpkin.todoapp.data.remote.responses.GetItemByIdResponse
import com.cleverpumpkin.todoapp.data.remote.responses.GetItemListResponse
import com.cleverpumpkin.todoapp.domain.id_handlers.DeviceIdProvider
import com.cleverpumpkin.todoapp.domain.mapper.toDto
import com.cleverpumpkin.todoapp.domain.models.Response
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val api: TodoApi,
    private val preferences: SharedPreferences,
    private val deviceIdProvider: DeviceIdProvider
) : TodoItemsRepository {

    private val _todoItemsFlow = MutableStateFlow<List<TodoItem>>(emptyList())
    override val todoItemsFlow: StateFlow<List<TodoItem>> get() = _todoItemsFlow.asStateFlow()

    override suspend fun fetchTodoItems(): Response<GetItemListResponse> =
        withContext(Dispatchers.IO) {
            val response = api.getItems()
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
                _todoItemsFlow.update { response.result.items.map { it.toDomain() } }
            }
            response
        }

    override suspend fun addTodoItem(item: TodoItem) {
        withContext(Dispatchers.IO) {
            val revision = preferences.getInt(PreferenceKeys.REVISION, 0).toString()
            val response =
                api.addItem(item = item.toDto(deviceIdProvider.provideDeviceId()), revision)
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
        }
        fetchTodoItems()
    }



    override suspend fun deleteTodoItem(itemId: String) {
        withContext(Dispatchers.IO) {
            val revision = preferences.getInt(PreferenceKeys.REVISION, 0).toString()
            val response = api.deleteItemById(itemId, revision)
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
        }
        fetchTodoItems()
    }


    override suspend fun findItemById(id: String): Response<GetItemByIdResponse> =
        withContext(Dispatchers.IO) {
            val response = api.getItemById(id)
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
            fetchTodoItems()
            response
        }

    override suspend fun updateTodoItem(item: TodoItem) {
        withContext(Dispatchers.IO) {
            val revision = preferences.getInt(PreferenceKeys.REVISION, 0).toString()
            val response = api.changeItem(item.toDto(deviceIdProvider.provideDeviceId()), revision)
            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
            fetchTodoItems()
        }
    }

    private fun rewriteRevision(revision: Int) {
        preferences.edit().putInt(
            PreferenceKeys.REVISION,
            revision
        ).apply()
    }
}
