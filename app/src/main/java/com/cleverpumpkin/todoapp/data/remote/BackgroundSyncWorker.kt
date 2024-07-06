package com.cleverpumpkin.todoapp.data.remote

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cleverpumpkin.todoapp.domain.models.Response
import com.cleverpumpkin.todoapp.domain.repository.TodoItemsRepository
import javax.inject.Inject

/**
 * Worker class responsible for background synchronization of todo items.
 */

class BackgroundSyncWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var repository: TodoItemsRepository

    override suspend fun doWork(): Result {
        val response = repository.fetchTodoItems()
        return when (response) {
            is Response.Failure -> Result.retry()
            is Response.Success -> Result.success()
        }
    }

    companion object {
        const val WORKER_ID = "sync_worker_id"
    }
}
