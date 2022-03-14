package com.androidtasks.crashesinfo_reporter.data

/**
 * the crash data fields on the crash report
 */
data class CrashDataFields(
    val appData: AppDataFields,
    val deviceDataField: DeviceDataFields,
    val stackTrace: String
)