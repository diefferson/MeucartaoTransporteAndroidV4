package br.com.disapps.data.api

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.app.DownloadManager.Request
import android.os.Environment
import br.com.disapps.data.R

class CustomDownloadManager(val context: Context) {

    private val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    fun download(fileName :String, url: String, token:String, downloadDescription :String, met: String) : Long{
        return try {
            val uri = Uri.parse("$url?met=$met")
            val request = Request(uri)
            request.addRequestHeader("Authorization", token)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            request.setTitle(context.getString(R.string.app_name))
            request.setDescription(downloadDescription)
            request.setVisibleInDownloadsUi(false)
            downloadManager.enqueue(request)
        }catch (e:Exception){
            -1
        }
    }
}
