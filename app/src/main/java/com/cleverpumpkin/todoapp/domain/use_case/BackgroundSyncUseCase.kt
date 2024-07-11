package com.cleverpumpkin.todoapp.domain.use_case

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cleverpumpkin.todoapp.data.background_syncer.BackgroundSyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Use case for scheduling background synchronization of todo items.
 */

class BackgroundSyncUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun sync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val fetchItemsRequest =
            PeriodicWorkRequestBuilder<BackgroundSyncWorker>(REPEAT_INTERVAL, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            BackgroundSyncWorker.WORKER_ID,
            ExistingPeriodicWorkPolicy.KEEP,
            fetchItemsRequest
        )
    }

    companion object {
        private const val REPEAT_INTERVAL = 8L
    }
}
