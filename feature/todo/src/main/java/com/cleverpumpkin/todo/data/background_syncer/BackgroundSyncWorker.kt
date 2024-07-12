package com.cleverpumpkin.todo.data.background_syncer

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cleverpumpkin.networ.domain.response_wrapper.Response
import com.cleverpumpkin.todo.domain.repository.TodoItemsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Worker class responsible for background synchronization of todo items.
 */
@HiltWorker
class BackgroundSyncWorker @AssistedInject constructor(
    private val repository: TodoItemsRepository,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val response = repository.fetchTodoItems()
        return when (response) {
            is Response.Failure -> Result.failure()
            is Response.Success -> Result.success()
        }
    }

    companion object {
        const val WORKER_ID = "sync_worker_id"
    }
}
