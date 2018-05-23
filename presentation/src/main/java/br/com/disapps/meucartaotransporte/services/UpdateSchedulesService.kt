package br.com.disapps.meucartaotransporte.services

import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import br.com.disapps.domain.interactor.schedules.SaveAllSchedulesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.cancelNotification
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import org.koin.android.ext.android.inject

class UpdateSchedulesService : BaseService(){

    private val saveAllSchedulesJsonUseCase : SaveAllSchedulesJson by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true
            isManual = intent?.extras?.getBoolean(IS_MANUAL)?:false

            if(isManual){
                showNotification(text = getString(R.string.updating_schedules), infinityProgress = true,
                        action = getAction(NotificationActionReceiver.CANCEL_ACTION))
                isComplete.observe(this, Observer {
                    if(it != null){
                        if(it){
                            showNotification(text =  getString(R.string.update_schedules_success),
                                    action = getAction(NotificationActionReceiver.CANCEL_ACTION))
                        }else{
                            showNotification(text =  getString(R.string.update_schedules_error),
                                    action = getAction(NotificationActionReceiver.RETRY_ACTION))
                        }
                        stopSelf()
                    }
                })
            }

            saveSchedules()
        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelNotification(this,getUpdateDataNotification(UpdateData.SCHEDULES).id )
        saveAllSchedulesJsonUseCase.dispose()
    }

    private fun saveSchedules(){

        saveAllSchedulesJsonUseCase.execute(SaveAllSchedulesJson.Params(cacheDir.absolutePath+"/schedules.json", updateProgressListener),
            onError = {
                isComplete.value = false
            },

            onComplete = {
                isComplete.value = true
                ScheduleJob.schedule(this, ScheduleJob.SCHEDULE_TYPE)
            }
        )

    }

    private val updateProgressListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            showNotification(text = getString(R.string.updating_schedules), progress =  percent, action = getAction(NotificationActionReceiver.CANCEL_ACTION))
        }
    }

    private fun showNotification(text:String,action: NotificationCompat.Action, progress :Int = 0, infinityProgress: Boolean = false){
        showCustomNotification(context = this@UpdateSchedulesService,
                channel = getUpdateDataNotification(UpdateData.SCHEDULES).channel,
                notificationId = getUpdateDataNotification(UpdateData.SCHEDULES).id,
                text = text,
                sortKey = "2",
                progress = progress,
                infinityProgress = infinityProgress,
                action = action)
    }

    fun getAction(typeAction : String) : NotificationCompat.Action {
        val intentAction = Intent(this, NotificationActionReceiver::class.java).apply {
            putExtra(NotificationActionReceiver.ACTION, typeAction)
            putExtra(NotificationActionReceiver.SERVICE, NotificationActionReceiver.SCHEDULE_SERVICE)
            putExtra(NotificationActionReceiver.CITY, City.CWB)
        }

        val pIntent = PendingIntent.getBroadcast(this, 1, intentAction, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Action(0, getString(R.string.cancel), pIntent)
    }

    companion object {
        private const val IS_MANUAL = "manual"
        fun startService(context: Context, manual :Boolean = true){
            try {
                context.startService(Intent(context, UpdateSchedulesService::class.java).apply {
                    putExtra(IS_MANUAL, manual)
                })
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}