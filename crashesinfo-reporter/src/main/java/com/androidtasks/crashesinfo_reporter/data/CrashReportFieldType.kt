package com.androidtasks.crashesinfo_reporter.data

enum class CrashReportFieldType(val type: String) {
    AppData("appData"),
    DeviceData("deviceData"),
    StackTraceData("stackTraceData"),
    CreatedAt("createdAt")
}