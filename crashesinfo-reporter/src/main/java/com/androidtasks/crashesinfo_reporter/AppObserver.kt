package com.androidtasks.crashesinfo_reporter

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.androidtasks.crashesinfo_reporter.scheduler.SenderScheduler

private const val TAG = "AppObserver"

class AppObserver(private val schedulerSendReport: SenderScheduler): DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d(TAG, "onCreate: ")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(TAG, "onStart: ")
        schedulerSendReport.startScheduleSend()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(TAG, "onStop: ")
        schedulerSendReport.stopScheduleSend()
    }
    
    


//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    fun onForeground() {
//        // App goes to foreground
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    fun onBackground() {
//        // App goes to background
//    }
}