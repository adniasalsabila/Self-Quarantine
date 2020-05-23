package org.d3if4133.side_project_adnia

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*

class BackupWorker(context : Context, params : WorkerParameters)
    : Worker(context, params) {

    companion object {
        fun run() : LiveData<WorkInfo> {
            val work = OneTimeWorkRequestBuilder<BackupWorker>().build()
            WorkManager.getInstance().enqueue(work)

            return WorkManager.getInstance().getWorkInfoByIdLiveData(work.id)
        }
    }

    override fun doWork(): Result {


        return Result.success()
    }
}