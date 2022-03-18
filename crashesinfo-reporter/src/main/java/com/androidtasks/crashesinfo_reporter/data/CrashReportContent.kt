package com.androidtasks.crashesinfo_reporter.data

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

const val TAG = "CrashReportData"

/**
 * Stores a crash report data
 */
class CrashReportContent {
    private val mCrashData: JSONObject

    constructor() {
        mCrashData = JSONObject()
    }

    constructor(json: String) {
        mCrashData = JSONObject(json)
    }

    operator fun get(key: String): Any? {
        return mCrashData.opt(key)
    }

    fun containsKey(key: String): Boolean {
        return mCrashData.has(key)
    }

    fun getCrashContentStringFormat() = mCrashData.toString()

    @Synchronized
    fun <T> put(key: String, value: T) {
        try {
            mCrashData.put(key, value)
        } catch (e: JSONException) {
            Log.d(TAG, "put: Failed to put value into CrashReportData: $value")
        }
    }

    fun toMap(): Map<String, Any?> {
        return mCrashData.keys().asSequence().map { it to get(it) }.toMap()
    }
}