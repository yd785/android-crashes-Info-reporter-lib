package com.androidtasks.crashesinforeporter

import android.app.Application
import android.util.Log
import com.androidtasks.crashesinfo_reporter.CrashReporterMain

private const val TAG = "CrashReporterTestApp"

class CrashReporterTestApp() : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")

        Log.d(TAG, "onCreate: initialize crash reporter")

        // initialize reporter
        CrashReporterMain.initialize(this)

    }
}