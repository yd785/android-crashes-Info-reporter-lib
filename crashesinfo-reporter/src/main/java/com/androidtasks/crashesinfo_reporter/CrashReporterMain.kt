package com.androidtasks.crashesinfo_reporter

import android.content.Context
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.androidtasks.crashesinfo_reporter.handler.ExceptionHandler
import com.androidtasks.crashesinfo_reporter.scheduler.ReportScheduler
import com.androidtasks.crashesinfo_reporter.sender.ReportSender
import com.androidtasks.crashesinfo_reporter.util.CollectCrashUtil
import kotlin.concurrent.thread

private const val TAG = "CrashReporterMain"

/**
 * Crash Report main start point for initializing handling
 * any exception occur in host application
 */
object CrashReporterMain {

    private lateinit var _mAppContext: Context
    private val exceptionHandler = ExceptionHandler()

    @JvmStatic
    fun initialize(context: Context) {
        Log.d(TAG, "initialize: ")
        this._mAppContext = context
        setUpExceptionHandler()

        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(AppObserver(ReportScheduler()))
    }

    /**
     * Context object for program
     */
    val mAppContext: Context
        get() = _mAppContext

    private fun setUpExceptionHandler() {
        if(!(Thread.getDefaultUncaughtExceptionHandler() is ExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
        }
    }

    /**
     * Use Catch the Exception from the application: API to catch Exception information
     */
    fun catchExceptionInfo(exception: Exception) {
        exceptionHandler.handleCaughtException(exception)
    }

//    fun testSendCrashesReport() {
//        ReportSender().sendCachedReport()
//    }


}