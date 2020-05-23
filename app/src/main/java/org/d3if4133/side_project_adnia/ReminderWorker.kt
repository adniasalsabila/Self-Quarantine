package org.d3if4133.side_project_adnia

import android.content.Context
import android.os.Build
import androidx.work.*
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import java.net.SocketException
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

class ReminderWorker(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val REMINDER_WORK_NAME = "reminder"
        private const val PARAM_NAME = "name" // optional - send parameter to worker
        // private const val RESULT_ID = "id"

        fun runAt() {
            val workManager = WorkManager.getInstance()

            // trigger at 8:30am
            val alarmTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalTime.of(8, 30)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            var now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val nowTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                now.toLocalTime()
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            // if same time, schedule for next day as well
            // if today's time had passed, schedule for next day
            if (nowTime == alarmTime || nowTime.isAfter(alarmTime)) {
                now = now.plusDays(1)
            }
            now = now.withHour(alarmTime.hour).withMinute(alarmTime.minute) // .withSecond(alarmTime.second).withNano(alarmTime.nano)
            val duration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Duration.between(LocalDateTime.now(), now)
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            Timber.d("runAt=${duration.seconds}s")

            // optional constraints
            /*
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
             */

            // optional data
            val data = workDataOf(PARAM_NAME to "Timer 01")

            val workRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                OneTimeWorkRequestBuilder<ReminderWorker>()
                    .setInitialDelay(duration.seconds, TimeUnit.SECONDS)
                    // .setConstraints(constraints)
                    .setInputData(data) // optional
                    .build()
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            workManager.enqueueUniqueWork(REMINDER_WORK_NAME, ExistingWorkPolicy.REPLACE, workRequest)
        }

        fun cancel() {
            Timber.d("cancel")
            val workManager = WorkManager.getInstance()
            workManager.cancelUniqueWork(REMINDER_WORK_NAME)
        }
    }


    override suspend fun doWork(): Result = coroutineScope {
        val worker = this@ReminderWorker
        val context = applicationContext

        val name = inputData.getString(PARAM_NAME)
        Timber.d("doWork=$name")

        var isScheduleNext = true
        try {
            // do something

            // possible to return result
            // val data = workDataOf(RESULT_ID to 1)
            // Result.success(data)

            Result.success()
        }
        catch (e: Exception) {
            // only retry 3 times
            if (runAttemptCount > 3) {
                Timber.d("runAttemptCount=$runAttemptCount, stop retry")
                return@coroutineScope Result.success()
            }

            // retry if network failure, else considered failed
            when(e.cause) {
                is SocketException -> {
                    Timber.e(e.toString(), e.message)
                    isScheduleNext = false
                    Result.retry()
                }
                else -> {
                    Timber.e(e)
                    Result.failure()
                }
            }
        }
        finally {
            // only schedule next day if not retry, else it will overwrite the retry attempt
            // - because we use uniqueName with ExistingWorkPolicy.REPLACE
            if (isScheduleNext) {
                runAt() // schedule for next day
            }
        }
    }
}