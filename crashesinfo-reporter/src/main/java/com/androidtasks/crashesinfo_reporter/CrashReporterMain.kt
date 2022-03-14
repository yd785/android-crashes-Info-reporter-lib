package com.androidtasks.crashesinfo_reporter

import android.content.Context
import com.androidtasks.crashesinfo_reporter.handler.ExceptionHandler
import com.androidtasks.crashesinfo_reporter.util.CollectCrashUtil
import kotlin.concurrent.thread

/**
 * Crash Report main start point for initializing handling
 * any exception occur in host application
 */
object CrashReporterMain {

    private lateinit var _mAppContext: Context
    private val exceptionHandler = ExceptionHandler()

    @JvmStatic
    fun initialize(context: Context) {
        this._mAppContext = context
        setUpExceptionHandler()
    }

    /**
     * Context object for program
     */
    val mAppContext: Context
        get() = _mAppContext

    fun setUpExceptionHandler() {
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


}