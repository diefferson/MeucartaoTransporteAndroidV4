package br.com.disapps.meucartaotransporte.services

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.disapps.data.utils.deleteFromCache
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.PreferencesRepository
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DownloadBroadcastReceiver : BroadcastReceiver(), KoinComponent{

    private val preferences : PreferencesRepository by inject()

    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent!= null){
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if(context != null){
                checkDownloadStatus(referenceId, context)
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
                            context.stopService(Intent(context, DownloadLinesService::class.java))
                            deleteFromCache(SaveLinesService.FILE_PATH)
                            showCustomNotification(context,
                                    SaveLinesService.NOTIFICATION_CHANNEL,
                                    SaveLinesService.NOTIFICATION_ID,
                                    context.getString(R.string.download_lines_error))
                        }
                        preferences.getIdDownloadSchedules() -> {
                            context.stopService(Intent(context, DownloadSchedulesService::class.java))
                            deleteFromCache(SaveSchedulesService.FILE_PATH)
                            showCustomNotification(context,
                                    SaveSchedulesService.NOTIFICATION_CHANNEL,
                                    SaveSchedulesService.NOTIFICATION_ID,
                                    context.getString(R.string.download_schedules_error))
                        }
                        preferences.getIdDownloadItinerariesCwb(),
                        preferences.getIdDownloadItinerariesMetropolitan() -> {
                            context.stopService(Intent(context, DownloadItinerariesService::class.java))
                            deleteFromCache(SaveItinerariesService.FILE_PATH)
                            showCustomNotification(context,
                                    SaveItinerariesService.NOTIFICATION_CHANNEL,
                                    SaveItinerariesService.NOTIFICATION_ID,
                                    context.getString(R.string.download_itineraries_error))
                        }
                        preferences.getIdDownloadShapesCwb(),
                        preferences.getIdDownloadShapesMetropolitan() -> {
                            context.stopService(Intent(context, DownloadShapesService::class.java))
                            deleteFromCache(SaveShapesService.FILE_PATH)
                            showCustomNotification(context,
                                    SaveShapesService.NOTIFICATION_CHANNEL,
                                    SaveShapesService.NOTIFICATION_ID,
                                    context.getString(R.string.download_shapes_error))
                        }
                    }
                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    when(referenceId){
                        preferences.getIdDownloadLines() -> {
                            context.stopService(Intent(context, DownloadLinesService::class.java))
                            SaveLinesService.startService(context)
                        }
                        preferences.getIdDownloadSchedules() -> {
                            context.stopService(Intent(context, DownloadSchedulesService::class.java))
                            SaveSchedulesService.startService(context)
                        }
                        preferences.getIdDownloadItinerariesCwb() -> {
                            context.stopService(Intent(context, DownloadItinerariesService::class.java))
                            SaveItinerariesService.startService(context, City.CWB)
                        }
                        preferences.getIdDownloadShapesCwb() -> {
                            context.stopService(Intent(context, DownloadItinerariesService::class.java))
                            SaveShapesService.startService(context, City.CWB)
                        }
                        preferences.getIdDownloadItinerariesMetropolitan() -> {
                            context.stopService(Intent(context, DownloadShapesService::class.java))
                            SaveItinerariesService.startService(context, City.MET)
                        }
                        preferences.getIdDownloadShapesMetropolitan() ->  {
                            context.stopService(Intent(context, DownloadShapesService::class.java))
                            SaveShapesService.startService(context, City.MET)
                        }
                    }
                }
            }
        }
    }
}