package br.com.disapps.meucartaotransporte.services

import android.app.Notification
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.schedules.SaveAllSchedulesJsonOnly
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.setupChannel
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SaveSchedulesService : BaseService(){

    private val saveAllSchedulesJsonUseCase : SaveAllSchedulesJsonOnly by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(SERVICE_NOTIFICATION_ID,getNotificationService())
            }else{
                showCustomNotification(this@SaveSchedulesService, NOTIFICATION_CHANNEL, NOTIFICATION_ID,getString(R.string.updating_schedules), true)
            }

            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        showCustomNotification(this@SaveSchedulesService,  NOTIFICATION_CHANNEL, NOTIFICATION_ID,getString(R.string.update_schedules_success))
                    }else{
                        showCustomNotification(this@SaveSchedulesService, NOTIFICATION_CHANNEL, NOTIFICATION_ID,getString(R.string.update_schedules_error))
                    }

                    stopService(Intent(this, DownloadSchedulesService::class.java))
                    stopSelf()
                }
            })

            saveSchedules()

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getNotificationService(): Notification? {
        setupChannel(this, NOTIFICATION_CHANNEL)
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.updating_schedules))
                .setOnlyAlertOnce(true)
                .setProgress(0, 100,true)
                .setSmallIcon(R.drawable.bus)
                .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    private fun saveSchedules(){
        saveAllSchedulesJsonUseCase(this, SaveAllSchedulesJsonOnly.Params(FILE_PATH)).onFailure {
            launch(Dispatchers.Main) {
                isComplete.value = false
            }
        }.onFailure {
            ScheduleJob.schedule(this, ScheduleJob.SCHEDULE_TYPE)
            launch(Dispatchers.Main) {
                isComplete.value = true
            }
        }
    }


    companion object {
        var isRunning = false
        const val SERVICE_NOTIFICATION_ID = 532882
        val NOTIFICATION_ID = getUpdateDataNotification(UpdateData.SCHEDULES).id
        val NOTIFICATION_CHANNEL = getUpdateDataNotification(UpdateData.SCHEDULES).channel
        val FILE_PATH= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath+"/schedules.json"

        fun startService(context: Context){
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(Intent(context, SaveSchedulesService::class.java))
                } else {
                    context.startService(Intent(context, SaveSchedulesService::class.java))
                }
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}