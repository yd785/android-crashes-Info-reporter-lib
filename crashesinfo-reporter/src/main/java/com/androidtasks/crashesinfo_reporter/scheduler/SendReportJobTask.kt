package com.androidtasks.crashesinfo_reporter.scheduler

import android.util.Log
import com.androidtasks.crashesinfo_reporter.sender.ReportSender

private const val TAG = "SendReportJobTask"

class SendReportJobTask(private val sender: ReportSender): Runnable {

    override fun run() {
        Log.d(TAG, "run: start task of send report")
        sender.sendReport()
    }

}