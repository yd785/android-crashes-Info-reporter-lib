package com.androidtasks.crashesinfo_reporter.scheduler

/**
 * Sender scheduler
 */
interface SenderScheduler {
    fun startScheduleSend()
    fun stopScheduleSend()
}