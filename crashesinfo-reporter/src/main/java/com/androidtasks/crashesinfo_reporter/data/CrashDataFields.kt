package com.androidtasks.crashesinfo_reporter.data

import java.util.*

/**
 * the crash data fields on the crash report
 */
data class CrashDataFields(
    val appData: AppDataFields,
    val deviceData: DeviceDataFields,
    val stackTraceData: String,
    val createdAt: String
)