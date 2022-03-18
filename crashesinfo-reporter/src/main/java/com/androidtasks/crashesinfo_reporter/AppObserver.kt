package com.androidtasks.crashesinfo_reporter

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.androidtasks.crashesinfo_reporter.scheduler.SenderScheduler

private const val TAG = "AppObserver"

class AppObserver(private val schedulerSendReport: SenderScheduler) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        schedulerSendReport.startScheduleSend()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        schedulerSendReport.stopScheduleSend()
    }

}