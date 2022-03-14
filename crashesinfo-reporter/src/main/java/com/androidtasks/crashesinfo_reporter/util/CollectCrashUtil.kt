package com.androidtasks.crashesinfo_reporter.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.androidtasks.crashesinfo_reporter.data.AppDataFields
import com.androidtasks.crashesinfo_reporter.data.CrashReportContent
import com.androidtasks.crashesinfo_reporter.data.CrashReportFieldType
import com.androidtasks.crashesinfo_reporter.data.DeviceDataFields
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer

/**
 * Utility for collect crash data
 */
class CollectCrashUtil {

    companion object {

        /**
         * Collect crash data for report
         *
         * @param context the context of the application
         * @param ex the exception of the application
         */
        fun collectCrashInfo(context: Context, ex: Throwable, crashReportData: CrashReportContent) {
            collectAppData(context, crashReportData)
            collectDeviceData(crashReportData)
            collectStackTraceData(crashReportData, ex)
        }

        /**
         * collect crash application data and assign it in the report content
         * the collections fields which will be include in the report are:
         * app package name, app version name, app version code
         *
         * @param the context of the application
         * @param reportContent the report data of the exception
         */
        fun collectAppData(ctx: Context, reportContent: CrashReportContent) {
            // Get Package Manager
            val pm: PackageManager = ctx.getPackageManager()
            // Get package information for extract the application version name and code
            val pInfo: PackageInfo =
                pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES)
            if (pInfo != null) {
                val versionName = if (pInfo.versionName == null) "null" else pInfo.versionName
                val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    pInfo.longVersionCode.toString() + ""
                } else {
                    pInfo.versionCode.toString()
                }

                // assign the collect app data in the report content
                val appData = AppDataFields(ctx.packageName, versionName, versionCode)
                reportContent.put(CrashReportFieldType.AppData.toString(), appData)
            }
        }

        /**
         * collect crash device data and assign it in the report content
         * the collections fields which will be include in the report are:
         * app package name, app version name, app version code
         *
         * @param reportContent the report data of the exception
         */
        fun collectDeviceData(reportContent: CrashReportContent) {
            val verRelease =  Build.VERSION.RELEASE
            val verIncreamental = Build.VERSION.INCREMENTAL
            val verSDKNum = Build.VERSION.SDK_INT
            val deviceData = DeviceDataFields(verRelease, verIncreamental, verSDKNum)
            reportContent.put(CrashReportFieldType.DeviceData.toString(), deviceData)
        }

        /**
         * collect crash trace stack data commonly used in development, problems exception with
         * all the stack trace will be saved as string
         *
         * @param reportContent the report data of the exception
         */
        fun collectStackTraceData(reportContent: CrashReportContent, ex: Throwable) {
            val writer: Writer = StringWriter()
            val printWriter = PrintWriter(writer)
            ex.printStackTrace(printWriter);
            val crashTraceResult: String = writer.toString()

            reportContent.put(CrashReportFieldType.StackTraceData.toString(), crashTraceResult)
            printWriter.close()

        }
    }
}