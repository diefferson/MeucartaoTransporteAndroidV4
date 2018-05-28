package br.com.disapps.data.api

import android.os.AsyncTask
import android.os.PowerManager
import br.com.disapps.domain.listeners.DownloadProgressListener
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class DownloadTask(var downloadProgressListener: DownloadProgressListener) : AsyncTask<String, Int, String>() {

    private val mWakeLock: PowerManager.WakeLock? = null

    override fun doInBackground(vararg params: String?): String? {
        var input: InputStream? = null
        var output: OutputStream? = null
        var connection: HttpURLConnection? = null
        try {
            val url = URL(params[0])
            connection = (url.openConnection() as HttpURLConnection).apply {
                doOutput = true
                requestMethod = "POST"
            }

            val os = connection.outputStream
            OutputStreamWriter(os, "UTF-8").apply {
                write("met="+params[1])
                flush()
                close()
            }
            os.close()
            connection.connect()

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return ("Server returned HTTP " + connection.responseCode
                        + " " + connection.responseMessage)
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            val fileLength = connection.contentLength

            // download the file

            input = connection.inputStream
            output = FileOutputStream(params[2])

            val data = ByteArray(4096)
            var total: Long = 0
            var count = input!!.read(data)
            while (count != -1) {
                // allow canceling with back button
                if (isCancelled) {
                    input.close()
                    return null
                }
                total += count.toLong()
                // publishing the progress....
                if (fileLength > 0)
                // only if total length is known
                    publishProgress((total * 100 / fileLength).toInt())
                output.write(data, 0, count)
                count = input.read(data)
            }

        } catch (e: Exception) {
            return e.toString()
        } finally {
            try {
                if (output != null) {
                    output.close()
                }

                if (input != null) {
                    input.close()
                }

            } catch (ignored: IOException) {
            }

            if (connection != null)
                connection.disconnect()
        }

        return "OK"
    }

    override fun onProgressUpdate(vararg values: Int?) {
        if(values.isNotEmpty()){
            downloadProgressListener.onAttachmentDownloadUpdate(values[0]!!.toInt())
        }
    }
}
