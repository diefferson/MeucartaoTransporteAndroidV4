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

class DownloadSchedulesService: BaseService(){

    private val customDownloadManager : CustomDownloadManager by inject()
    private val preferences : PreferencesRepository by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!SaveSchedulesService.isRunning && ! isRunning){
            isRunning = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(SaveSchedulesService.SERVICE_NOTIFICATION_ID,getNotificationService())
            }
            downloadSchedules()
        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_SHORT).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getNotificationService(): Notification? {
        setupChannel(this, SaveSchedulesService.NOTIFICATION_CHANNEL)
        return NotificationCompat.Builder(this, SaveSchedulesService.NOTIFICATION_CHANNEL).build()
    }

    private fun downloadSchedules(){
        val idDownload = customDownloadManager.download(FILE_NAME, BuildConfig.DOWNLOAD_SCHEDULES, BuildConfig.DOWNLOAD_SCHEDULES_KEY, getString(R.string.downloading_schedules), "")
        preferences.setIdDownloadSchedules(idDownload)
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    companion object {
        var isRunning = false
        const val FILE_NAME = "schedules.json"
        fun startService(context: Context){
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(Intent(context, DownloadSchedulesService::class.java))
                } else {
                    context.startService(Intent(context, DownloadSchedulesService::class.java))
                }
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}
