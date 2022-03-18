package com.androidtasks.crashesinfo_reporter.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * format log time
 */
fun Date.formatLogTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val timeFormat = dateFormat.format(this)
    return timeFormat
}