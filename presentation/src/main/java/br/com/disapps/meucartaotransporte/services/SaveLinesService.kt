package br.com.disapps.meucartaotransporte.services

import android.app.Notification
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import br.com.disapps.domain.interactor.lines.SaveAllLinesJsonOnly
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.setupChannel
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class SaveLinesService : BaseService(){

    private val saveAllLinesJsonUseCase: SaveAllLinesJsonOnly by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(SERVICE_NOTIFICATION_ID, getNotificationService())
            }else{
                showCustomNotification(this@SaveLinesService, NOTIFICATION_CHANNEL, NOTIFICATION_ID, getString(R.string.updating_lines), true)
            }

            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        showCustomNotification(this@SaveLinesService, NOTIFICATION_CHANNEL, NOTIFICATION_ID, text = getString(R.string.update_lines_success))
                    }else{
                        showCustomNotification(this@SaveLinesService, NOTIFICATION_CHANNEL, NOTIFICATION_ID, text = getString(R.string.update_lines_error))
                    }

                    stopService(Intent(this, DownloadLinesService::class.java))
                    stopSelf()
                }
            })

            saveLines()

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_SHORT).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getNotificationService(): Notification? {
        setupChannel(this, NOTIFICATION_CHANNEL)
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.updating_lines))
                .setOnlyAlertOnce(true)
                .setProgress(0, 100,true)
                .setSmallIcon(R.drawable.bus)
                .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        saveAllLinesJsonUseCase.dispose()
    }

    private fun saveLines(){
        saveAllLinesJsonUseCase.execute(SaveAllLinesJsonOnly.Params(FILE_PATH),
            onError= {
                launch(UI) {
                    isComplete.value = false
                }
            },

            onComplete= {
                ScheduleJob.schedule(this, ScheduleJob.LINE_TYPE)
                launch(UI) {
                    isComplete.value = true
                }
            }
        )
    }

    companion object {
        const val SERVICE_NOTIFICATION_ID = 532880
        var isRunning = false
        val NOTIFICATION_ID = getUpdateDataNotification(UpdateData.LINES).id
        val NOTIFICATION_CHANNEL = getUpdateDataNotification(UpdateData.LINES).channel
        val FILE_PATH= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath+"/lines.json"

        fun startService(context: Context){
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(Intent(context, SaveLinesService::class.java))
                } else {
                    context.startService(Intent(context, SaveLinesService::class.java))
                }
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}