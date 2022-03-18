package com.androidtasks.crashesinfo_reporter.storage

import android.content.Context
import android.util.Log
import com.androidtasks.crashesinfo_reporter.data.CrashReportContent
import com.androidtasks.crashesinfo_reporter.util.CRASH_DIR
import com.androidtasks.crashesinfo_reporter.util.CRASH_FILE_EXTENSION
import com.androidtasks.crashesinfo_reporter.util.CRASH_FILE_NAME
import com.androidtasks.crashesinfo_reporter.util.formatLogTime
import java.io.*
import java.util.*

private const val TAG = "PersistFileStore"

/**
 * Handles persistence of [CrashReportContent]
 */
class PersistFileStore {

    /**
     * get all crashes files under the crash folder
     *
     * @param ctx the application context
     * @return Array<File> array of Files
     */
    fun getCrashFilesList(ctx: Context): Array<File> {
        val dir = File(ctx.filesDir, CRASH_DIR)
        return dir.listFiles()
    }

    /**
     * Stores the crash report data mappings in same fields Properties to the specified OutputStream
     */
    fun store(crashReportContent: CrashReportContent, ctx: Context) {
        //val timestamp = System.currentTimeMillis()

        Log.d(TAG, "store: crashReportContent " + crashReportContent.getCrashContentStringFormat())
        // get the format time for the saved file name
        val timeFormat = Date().formatLogTime()

        val fileName = timeFormat + "-" + CRASH_FILE_NAME + CRASH_FILE_EXTENSION

        // Define the File Path and its Name
        val dir = File(ctx.getFilesDir(), CRASH_DIR)
        if (!dir.exists()) {
            dir.mkdir()
        }

        try {
            val file = File(dir, fileName)
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            val crashContentString = crashReportContent.getCrashContentStringFormat()
            bufferedWriter.write(crashContentString)
            bufferedWriter.flush()
            bufferedWriter.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /**
     * load the crash report data from file in the storage as JSON string
     */
    fun load(file: File): String {
        Log.d(TAG, "load: file " + file)
        val crashDataString = StringBuilder()

        try {
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            crashDataString.append(bufferedReader.readLine())
            bufferedReader.close()
        } catch (ex: Exception) {
            ex.printStackTrace()

        }

        return crashDataString.toString()
    }

    /**
     * delete file from storage
     *
     * @param file the file to delete
     */
    fun deleteCrashFile(file: File): Boolean {
        return file.delete()
    }
}