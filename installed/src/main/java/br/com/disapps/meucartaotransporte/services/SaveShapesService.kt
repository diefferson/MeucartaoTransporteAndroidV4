package br.com.disapps.meucartaotransporte.services

import android.app.Notification
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import br.com.disapps.domain.interactor.shapes.SaveAllShapesJson
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.model.UpdateData
import br.com.disapps.meucartaotransporte.base.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.base.util.showCustomNotification
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class SaveShapesService : BaseService(){

    private val saveAllShapesJsonUseCase : SaveAllShapesJson by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){

            isRunning = true

            city = intent?.extras?.let{
                it.getSerializable(CITY)?.let {it as City}?: run {City.CWB}
            }?: run {
                City.CWB
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(SERVICE_NOTIFICATION_ID, getNotificationService())
            }else{
                showCustomNotification(this@SaveShapesService, NOTIFICATION_CHANNEL, NOTIFICATION_ID, getString(R.string.saving_data), true)
            }

            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        showCustomNotification(this@SaveShapesService, NOTIFICATION_CHANNEL, NOTIFICATION_ID, getString(R.string.update_shapes_success))
                    }else{
                        showCustomNotification(this@SaveShapesService, NOTIFICATION_CHANNEL, NOTIFICATION_ID, getString(R.string.update_shapes_error))
                    }

                    stopService(Intent(this, DownloadShapesService::class.java))
                    stopSelf()
                }
            })

            saveShapes(city)

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_SHORT).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getNotificationService(): Notification? {
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.saving_data))
                .setOnlyAlertOnce(true)
                .setProgress(0, 100,true)
                .setSmallIcon(R.drawable.bus)
                .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        saveAllShapesJsonUseCase.dispose()
    }

    private fun saveShapes(city: City){
        saveAllShapesJsonUseCase.execute(SaveAllShapesJson.Params(city, FILE_PATH),
            onError = {
                launch(UI) {
                    isComplete.value = false
                }
            },

            onComplete = {
                launch(UI) {
                    isComplete.value = true
                }
            }
        )
    }

    companion object {
        var isRunning = false
        private const val CITY = "city"
        const val SERVICE_NOTIFICATION_ID = 532222
        val NOTIFICATION_ID = getUpdateDataNotification(UpdateData.SHAPES).id
        val NOTIFICATION_CHANNEL = getUpdateDataNotification(UpdateData.SHAPES).channel
        val FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath+"/shapes.json"

        fun startService(context: Context, city: City){
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(Intent(context,  SaveShapesService::class.java).apply {
                        putExtra(CITY, city)
                    })
                } else {
                    context.startService(Intent(context, SaveShapesService::class.java).apply {
                        putExtra(CITY, city)
                    })
                }
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}