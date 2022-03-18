package com.androidtasks.crashesinfo_reporter.scheduler

import com.androidtasks.crashesinfo_reporter.sender.ReportSender

class SendReportJobTask(private val sender: ReportSender): Runnable {

    override fun run() {
        sender.sendReport()
    }
}