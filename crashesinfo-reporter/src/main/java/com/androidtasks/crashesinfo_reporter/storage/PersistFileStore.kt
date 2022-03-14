package com.androidtasks.crashesinfo_reporter.storage

import android.content.Context
import com.androidtasks.crashesinfo_reporter.data.CrashReportContent
import com.androidtasks.crashesinfo_reporter.util.CRASH_DIR
import com.androidtasks.crashesinfo_reporter.util.CRASH_FILE_EXTENSION
import com.androidtasks.crashesinfo_reporter.util.CRASH_FILE_NAME
import com.androidtasks.crashesinfo_reporter.util.formatLogTime
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class PersistFileStore {

    /**
     * get all crashes files under the crash folder
     *
     * @param ctx the application context
     * @return Array<File> array of Files
     */
    fun getCrashFilesList(ctx: Context): Array<File> {
        val dir = File(ctx.getFilesDir(), CRASH_DIR)
        return dir.listFiles()
    }

    /**
     * Stores the crash report data mappings in same fields Properties to the specified OutputStream
     */
    fun store(crashReportContent: CrashReportContent, ctx: Context) {
        //val timestamp = System.currentTimeMillis()

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
            val crashContentString = crashReportContent.toString()
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
        val resultString = StringBuilder()
        try {
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            var line: String = bufferedReader.readLine()
            while (line != null) {
                resultString.append(line).append("\n")
                line = bufferedReader.readLine()
            }
            bufferedReader.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return resultString.toString()
    }

    fun deleteCrashFile(file: File): Boolean {
        return file.delete()
    }
}