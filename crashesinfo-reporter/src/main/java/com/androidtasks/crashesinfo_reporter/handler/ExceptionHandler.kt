package com.androidtasks.crashesinfo_reporter.handler

import android.content.Context
import com.androidtasks.crashesinfo_reporter.CrashReporterMain.mAppContext
import com.androidtasks.crashesinfo_reporter.CrashReporterMain
import com.androidtasks.crashesinfo_reporter.data.CrashReportContent
import com.androidtasks.crashesinfo_reporter.storage.PersistFileStore
import com.androidtasks.crashesinfo_reporter.util.CollectCrashUtil
import com.androidtasks.crashesinfo_reporter.util.CollectCrashUtil.Companion.collectCrashInfo
import kotlin.concurrent.thread

// log TAG for class
private const val TAG = "ExceptionHandler"

/**
 * Handle the exceptions - captures the stacktrace for every unhandled error, and generates a diagnostic report data
 */
class ExceptionHandler : Thread.UncaughtExceptionHandler {

    /**
     * System default UncaughtException processing class
     */
    private val mDefaultExHandler = Thread.getDefaultUncaughtExceptionHandler()

    /**
     * Used to store exception data information
     */
    private val crashReportData = CrashReportContent()

    /**
     * the persistence storage for save and load the report data
     */
    private val fileStore: PersistFileStore = PersistFileStore()

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultExHandler != null) {
            // if the user does not handle any exception Let the default exception handler of the system handle
            mDefaultExHandler.uncaughtException(thread, ex)
        } else {
            handleException(ex)
        }
    }

    /**
     * Handle the exception : collect data for crash report
     *
     * @param ex the exception to handle
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }

        collectCrashInfo(mAppContext, ex, crashReportData)
        fileStore.store(crashReportData, mAppContext)

        return true
    }

    public fun handleCaughtException(ex: Exception) {
        thread(start = true) {
            handleException(ex)
        }
    }

}