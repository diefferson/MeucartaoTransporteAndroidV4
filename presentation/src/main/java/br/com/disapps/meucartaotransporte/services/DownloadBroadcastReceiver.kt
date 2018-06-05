package br.com.disapps.meucartaotransporte.services

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.disapps.data.utils.deleteFromCache
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.PreferencesRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DownloadBroadcastReceiver : BroadcastReceiver(), KoinComponent{

    private val preferences : PreferencesRepository by inject()

    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent!= null){

            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val action = intent.action

            if(context != null){
                checkDownloadStatus(referenceId, context)
            }

            when(action){
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE ->{
                }
            }
        }
    }

    private fun checkDownloadStatus(referenceId: Long, context: Context) {
        val query = DownloadManager.Query()

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        query.setFilterById(referenceId)
        val cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            val status = cursor.getInt(columnIndex)
            when (status) {
                DownloadManager.STATUS_FAILED -> {
                    when(referenceId){
                        preferences.getIdDownloadLines() -> {
                            context.stopService(Intent(context, SaveLinesService::class.java))
                        }
                        preferences.getIdDownloadSchedules() -> context.stopService(Intent(context, SaveSchedulesService::class.java))
                        preferences.getIdDownloadItinerariesCwb() -> {
                            context.stopService(Intent(context, DownloadItinerariesService::class.java))
                            deleteFromCache(SaveItinerariesService.FILE_PATH_CWB)
                        }
                        preferences.getIdDownloadShapesCwb() -> {
                            context.stopService(Intent(context, DownloadShapesService::class.java))
                            deleteFromCache(SaveShapesService.FILE_PATH_CWB)
                        }
                        preferences.getIdDownloadItinerariesMetropolitan() -> {
                            context.stopService(Intent(context, DownloadItinerariesService::class.java))
                            deleteFromCache(SaveItinerariesService.FILE_PATH_MET)
                        }
                        preferences.getIdDownloadShapesMetropolitan() ->  {
                            context.stopService(Intent(context, DownloadShapesService::class.java))
                            deleteFromCache(SaveItinerariesService.FILE_PATH_MET)
                        }
                    }
                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    when(referenceId){
                        preferences.getIdDownloadLines() -> SaveLinesService.startService(context, true)
                        preferences.getIdDownloadSchedules() -> SaveSchedulesService.startService(context, true)
                        preferences.getIdDownloadItinerariesCwb() -> SaveItinerariesService.startService(context, City.CWB)
                        preferences.getIdDownloadShapesCwb() -> SaveShapesService.startService(context, City.CWB)
                        preferences.getIdDownloadItinerariesMetropolitan() -> SaveItinerariesService.startService(context, City.MET)
                        preferences.getIdDownloadShapesMetropolitan() ->  SaveShapesService.startService(context, City.MET)
                    }
                }
            }
        }
    }
}