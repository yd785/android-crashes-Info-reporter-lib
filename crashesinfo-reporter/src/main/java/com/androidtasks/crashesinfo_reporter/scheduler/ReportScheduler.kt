package com.androidtasks.crashesinfo_reporter.scheduler

import android.util.Log
import com.androidtasks.crashesinfo_reporter.sender.ReportSender
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

private const val TAG = "ReportScheduler"

/**
 * Scheduler periodically background task to report about the crashes to End point server
 */
class ReportScheduler : SenderScheduler {

    var mThreadPoolExecuter: ScheduledThreadPoolExecutor? = null

    /**
     * start schedule send report of crashes job
     */
    override fun startScheduleSend() {
        Log.d(TAG, "startScheduleSend: ")
        mThreadPoolExecuter.let { mThreadPoolExecuter = ScheduledThreadPoolExecutor(1) }
        Log.d(TAG, "startScheduleSend: mThreadPoolExecuter $mThreadPoolExecuter")
        mThreadPoolExecuter?.scheduleAtFixedRate(
            SendReportJobTask(ReportSender()),
            0,
            1,
            TimeUnit.MINUTES
        )
    }

    /**
     * stop schedule sending report job
     */
    override fun stopScheduleSend() {
        Log.d(TAG, "stopScheduleSend: ")
        mThreadPoolExecuter?.shutdown()
    }
}