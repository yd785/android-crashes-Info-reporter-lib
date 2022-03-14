package com.androidtasks.crashesinfo_reporter.sender

import android.util.Log
import com.androidtasks.crashesinfo_reporter.CrashReporterMain
import com.androidtasks.crashesinfo_reporter.storage.PersistFileStore
import com.androidtasks.crashesinfo_reporter.util.END_POINT_BASE_URL
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

private const val TAG = "ReportSender"

/**
 * Send the report of crashes to remote server
 * the sender collect the report data from local cach and
 * transfer them to server
 */
class ReportSender {

    /**
     * Use thread pool for long running process communicate with local cache persistence and remote server
     */
    val threadPool = Executors.newFixedThreadPool(4)

    /**
     * The persistent storage contain the report file data
     */
    val fileStore: PersistFileStore = PersistFileStore()

    /**
     * Sends a cached report that has already been deserialized, then deletes it from disk.
     * Performing network requests in the background. we'll use Java's powerful Executor framework instead
     * RxJava or Kotlin Coroutines in order to prevent the needs of dependencies from host apps and also
     * due to its dependency libraries size and the fact that we only need to perform HTTP calls to 1-2 different endpoints
     */
    fun sendCachedReport() {
        // Traverse the crash folder in the disk internal storage to get each file
        val files = fileStore.getCrashFilesList(CrashReporterMain.mAppContext)
        for (file in files) {
            // get the payload report data from the crash file in the cache storage
            // Performing network requests in the background.
            threadPool.submit {
                val reportData = fileStore.load(file)
                val success = sendRequestReportToServer(reportData)
                if (success) {
                    fileStore.deleteCrashFile(file)
                }
            }
        }
    }

    /**
     * Send the report data over HTTP POST request.
     *
     * @param reportPayload the report payload data string from JSON format to send to End Point
     *
     */
    fun sendRequestReportToServer(reportPayload: String): Boolean {
        var urlConnection: HttpURLConnection? = null

        try {
            val url = URL(reportUrl);
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.setRequestProperty("Content-Type", "application/json")
            urlConnection.setRequestMethod("POST")
            urlConnection.setDoOutput(true)
            urlConnection.setDoInput(true)
            urlConnection.setChunkedStreamingMode(0)

            val out: OutputStream = BufferedOutputStream(urlConnection.outputStream)
            val writer = BufferedWriter(
                OutputStreamWriter(
                    out, "UTF-8"
                )
            )
            writer.write(reportPayload)
            writer.flush()

            val code = urlConnection.getResponseCode()
            if (code != 201 || code != 200) {
                throw IOException("Invalid response from server: $code")
            }

            val rd = BufferedReader(
                InputStreamReader(
                    urlConnection.inputStream
                )
            )
            var line: String?
            while (rd.readLine().also { line = it } != null) {
                line?.let { Log.i(TAG, it) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            urlConnection?.disconnect()
        }

        return true
    }

    companion object {
        const val reportUrl = END_POINT_BASE_URL + "/api/exceptions"
    }
}