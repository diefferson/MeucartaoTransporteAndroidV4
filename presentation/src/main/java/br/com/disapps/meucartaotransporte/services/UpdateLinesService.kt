package br.com.disapps.meucartaotransporte.services

import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import br.com.disapps.domain.interactor.lines.SaveAllLinesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.cancelNotification
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import org.koin.android.ext.android.inject

class UpdateLinesService : BaseService(){

    private val saveAllLinesJsonUseCase: SaveAllLinesJson by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true
            isManual = intent?.extras?.getBoolean(IS_MANUAL)?:false

            if(isManual){
                showNotification(text =getString(R.string.updating_lines), infinityProgress = true,
                        action = getAction(NotificationActionReceiver.CANCEL_ACTION))
                isComplete.observe(this, Observer {
                    if(it != null){
                        if(it){
                            showNotification(text = getString(R.string.update_lines_success),
                                    action = getAction(NotificationActionReceiver.CANCEL_ACTION))
                        }else{
                            showNotification(text = getString(R.string.update_lines_error),
                                    action = getAction(NotificationActionReceiver.RETRY_ACTION))
                        }
                        stopSelf()
                    }
                })
            }

            saveLines()

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelNotification(this,getUpdateDataNotification(UpdateData.LINES).id )
        saveAllLinesJsonUseCase.dispose()
    }

    private fun saveLines(){

        saveAllLinesJsonUseCase.execute(SaveAllLinesJson.Params(cacheDir.absolutePath+"/lines.json", updateProgressListener),
                onError= {
                    isComplete.value = false
                },

                onComplete= {
                    isComplete.value = true
                    ScheduleJob.schedule(this, ScheduleJob.LINE_TYPE)
                }
        )
    }

    private val updateProgressListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            showNotification(text = getString(R.string.updating_lines), progress =  percent, action = getAction(NotificationActionReceiver.CANCEL_ACTION))
        }
    }

    private fun showNotification(text:String,action: NotificationCompat.Action, progress :Int = 0, infinityProgress: Boolean = false){
        showCustomNotification(context = this@UpdateLinesService,
                channel = getUpdateDataNotification(UpdateData.LINES).channel,
                notificationId = getUpdateDataNotification(UpdateData.LINES).id,
                text = text,
                sortKey = "1",
                progress = progress,
                infinityProgress = infinityProgress,
                action = action)
    }

    fun getAction(typeAction : String) : NotificationCompat.Action {
        val intentAction = Intent(this, NotificationActionReceiver::class.java).apply {
            putExtra(NotificationActionReceiver.ACTION, typeAction)
            putExtra(NotificationActionReceiver.SERVICE, NotificationActionReceiver.LINE_SERVICE)
            putExtra(NotificationActionReceiver.CITY, City.CWB)
        }

        val pIntent = PendingIntent.getBroadcast(this, 1, intentAction, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Action(0, getString(R.string.cancel), pIntent)
    }

    companion object {
        private const val IS_MANUAL = "manual"
        fun startService(context: Context, manual : Boolean = true){
            try {
                context.startService(Intent(context, UpdateLinesService::class.java).apply {
                    putExtra(IS_MANUAL, manual)
                })
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}