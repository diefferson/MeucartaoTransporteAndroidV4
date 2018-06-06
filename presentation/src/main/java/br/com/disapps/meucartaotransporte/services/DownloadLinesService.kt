package br.com.disapps.meucartaotransporte.services

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import br.com.disapps.data.BuildConfig
import br.com.disapps.data.api.CustomDownloadManager
import br.com.disapps.domain.repository.PreferencesRepository
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.util.setupChannel
import org.koin.android.ext.android.inject

class DownloadLinesService: BaseService(){

    private val customDownloadManager : CustomDownloadManager by inject()
    private val preferences : PreferencesRepository by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!SaveLinesService.isRunning && !isRunning){
            isRunning = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(SaveLinesService.SERVICE_NOTIFICATION_ID, getNotificationService())
            }
            downloadLines()
        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_SHORT).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getNotificationService(): Notification? {
        setupChannel(this, SaveLinesService.NOTIFICATION_CHANNEL)
        return NotificationCompat.Builder(this, SaveLinesService.NOTIFICATION_CHANNEL).build()
    }

    private fun downloadLines(){
        val idDownload = customDownloadManager.download(FILE_NAME, BuildConfig.DOWNLOAD_LINES, BuildConfig.DOWNLOAD_LINES_KEY, getString(R.string.downloading_lines), "")
        preferences.setIdDownloadLines(idDownload)
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    companion object {
        var isRunning = false
        const val FILE_NAME = "lines.json"
        fun startService(context: Context){
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(Intent(context, DownloadLinesService::class.java))
                } else {
                    context.startService(Intent(context, DownloadLinesService::class.java))
                }
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}
