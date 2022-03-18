package com.androidtasks.crashesinfo_reporter.handler

import com.androidtasks.crashesinfo_reporter.CrashReporterMain.mAppContext
import com.androidtasks.crashesinfo_reporter.data.CrashReportContent
import com.androidtasks.crashesinfo_reporter.storage.PersistFileStore
import com.androidtasks.crashesinfo_reporter.util.CollectCrashUtil.Companion.collectCrashInfo
import kotlin.concurrent.thread

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
        handleException(ex)
        mDefaultExHandler?.uncaughtException(thread, ex)
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

    fun handleCaughtException(ex: Exception) {
        thread(start = true) {
            handleException(ex)
        }
    }
}