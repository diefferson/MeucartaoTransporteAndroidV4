package br.com.disapps.data.api

import br.com.disapps.domain.listeners.DownloadProgressListener
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

class DownloadProgressResponseBody(private val responseBody: ResponseBody?,
                                   private val progressListener: DownloadProgressListener?) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody?.contentType()
    }

    override fun contentLength(): Long {
        return responseBody?.contentLength()?:0
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody?.source()!!))
        }
        return bufferedSource!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead :Float = 0f

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0

                val percent: Float = if(bytesRead == -1L) 100f else ((totalBytesRead/contentLength()) * 100)

                progressListener?.onAttachmentDownloadUpdate(percent = percent.toInt())

                return bytesRead
            }
        }

    }
}
