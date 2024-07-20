package com.cleverpumpkin.todo.data.repository

import android.content.SharedPreferences
import com.cleverpumpkin.cor.data.preference.PreferenceKeys
import com.cleverpumpkin.cor.id_handlers.DeviceIdProvider
import com.cleverpumpkin.database.data.TodoDao
import com.cleverpumpkin.networ.domain.api.TodoApi
import com.cleverpumpkin.networ.domain.response_wrapper.Response
import com.cleverpumpkin.networ.domain.responses.AddItemResponse
import com.cleverpumpkin.networ.domain.responses.ChangeItemResponse
import com.cleverpumpkin.networ.domain.responses.DeleteItemByIdResponse
import com.cleverpumpkin.networ.domain.responses.GetItemListResponse
import com.cleverpumpkin.todo.domain.mappers.toDomain
import com.cleverpumpkin.todo.domain.mappers.toDto
import com.cleverpumpkin.todo.domain.mappers.toEntity
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
    private val deviceIdProvider: DeviceIdProvider,
    private val dao: TodoDao
) : TodoItemsRepository {
    private val _todoItemsFlow = MutableStateFlow<List<TodoItem>>(emptyList())
    override val todoItemsFlow: StateFlow<List<TodoItem>> get() = _todoItemsFlow.asStateFlow()

    override suspend fun fetchTodoItems(): Response<GetItemListResponse> =
        withContext(Dispatchers.IO) {
            val revision = getRevision()
            val token = getToken()
            val response = api.getItems(revision, token)

            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
                dao.upsert(response.result.items.map { it.toEntity() })
                _todoItemsFlow.update {
                    dao.getAll().filter { !it.isDeleted }.map { it.toDomain() }
                }
            }
            response
        }

    override suspend fun addTodoItem(item: TodoItem): Response<AddItemResponse> =
        withContext(Dispatchers.IO) {
            val lastUpdatedBy = deviceIdProvider.provideDeviceId()
            dao.upsert(item.toEntity(lastUpdatedBy))
            _todoItemsFlow.update {
                dao.getAll().filter { !it.isDeleted }.map { it.toDomain() }
            }

            val revision = getRevision()
            val token = getToken()
            val response = api.addItem(item.toDto(lastUpdatedBy), revision, token)

            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
            response
        }

    override suspend fun deleteTodoItem(item: TodoItem): Response<DeleteItemByIdResponse> =
        withContext(Dispatchers.IO) {
            val lastUpdatedBy = deviceIdProvider.provideDeviceId()
            dao.upsert(item.toEntity(lastUpdatedBy).copy(isDeleted = true))
            _todoItemsFlow.update {
                dao.getAll().filter { !it.isDeleted }.map { it.toDomain() }
            }

            val revision = getRevision()
            val token = getToken()
            val response = api.deleteItemById(item.id, revision, token)

            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
            response
        }

    override suspend fun findItemById(id: String): TodoItem = withContext(Dispatchers.IO) {
        dao.findItem(id).toDomain()
    }

    override suspend fun updateTodoItem(item: TodoItem): Response<ChangeItemResponse> =
        withContext(Dispatchers.IO) {
            val lastUpdatedBy = deviceIdProvider.provideDeviceId()
            dao.upsert(item.toEntity(lastUpdatedBy))
            _todoItemsFlow.update {
                dao.getAll().filter { !it.isDeleted }.map { it.toDomain() }
            }

            val revision = getRevision()
            val token = getToken()
            val response = api.changeItem(item.toDto(lastUpdatedBy), revision, token)

            if (response is Response.Success) {
                rewriteRevision(response.result.revision)
            }
            response
        }

    override suspend fun uploadTodoItems() {
        withContext(Dispatchers.IO) {
            val items = dao.getAll()
            val undeletedItems = items.filter { !it.isDeleted }.map { it.toDto() }
            val revision = getRevision()
            val token = getToken()
            api.updateItemList(undeletedItems, revision, token)
        }
    }

    private fun getRevision(): String = preferences.getInt(PreferenceKeys.REVISION, 0).toString()

    private fun getToken(): String = preferences.getString(PreferenceKeys.AUTH_KEY, "")!!


    private fun rewriteRevision(revision: Int) {
        preferences.edit().putInt(
            PreferenceKeys.REVISION,
            revision
        ).apply()
    }
}
