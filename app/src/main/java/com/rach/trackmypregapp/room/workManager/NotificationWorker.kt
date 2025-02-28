package com.rach.trackmypregapp.room.workManager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rach.trackmypregapp.notification.MyNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val myNotification: MyNotification
):CoroutineWorker(context ,params) {


        init {
            Log.d("NotificationWorker", "Worker instantiated")
        }


    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: return Result.failure()
        val message = inputData.getString("message") ?: return Result.failure()

       // Log.d("NotificationWorker", "Notification Triggered: $title - $message")

        myNotification.fireNotification(title,message)
        return Result.success()
    }


}