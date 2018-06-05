package br.com.disapps.meucartaotransporte.services

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import br.com.disapps.data.BuildConfig
import br.com.disapps.data.api.CustomDownloadManager
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.PreferencesRepository
import br.com.disapps.meucartaotransporte.R
import org.koin.android.ext.android.inject

class DownloadSchedulesService: BaseService(){

    private val customDownloadManager : CustomDownloadManager by inject()
    private val preferences : PreferencesRepository by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!isRunning){
            isRunning = true
            downloadLines()
        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun downloadLines(){
        val idDownload = customDownloadManager.download(SCHEDULES_FILE, BuildConfig.DOWNLOAD_SCHEDULES, BuildConfig.DOWNLOAD_SCHEDULES_KEY, getString(R.string.downloading_lines), "")
        preferences.setIdDownloadLines(idDownload)
    }

    companion object {
        const val SCHEDULES_FILE = "schedules.json"
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
