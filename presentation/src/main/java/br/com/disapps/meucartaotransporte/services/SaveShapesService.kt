package br.com.disapps.meucartaotransporte.services

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
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
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

            val id = if(city == City.CWB){
                getUpdateDataNotification(UpdateData.CWB_SHAPES).id
            }else{
                getUpdateDataNotification(UpdateData.MET_SHAPES).id
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(id, NotificationCompat.Builder(this, CHANNEL)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText( getString(R.string.saving_data))
                        .setOnlyAlertOnce(true)
                        .setSmallIcon(R.drawable.bus)
                        .build())
            }else{
                showNotification(this@SaveShapesService, city,text =getString(R.string.saving_data), infinityProgress = true)
            }

            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        showNotification(this@SaveShapesService, city,text =  getString(R.string.update_shapes_success))
                    }else{
                        showNotification(this@SaveShapesService, city,text =  getString(R.string.update_shapes_error))
                    }

                    stopService(Intent(this, DownloadShapesService::class.java))
                    stopSelf()
                }
            })

            saveShapes(city)

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        saveAllShapesJsonUseCase.dispose()
    }

    private fun saveShapes(city: City){
        saveAllShapesJsonUseCase.execute(SaveAllShapesJson.Params(city, if(city == City.CWB)  FILE_PATH_CWB  else FILE_PATH_MET),
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
        private const val CHANNEL = "UPDATING_DATA"
        private const val CITY = "city"
        private val BASE_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        val FILE_PATH_CWB = "$BASE_DIRECTORY/shapesCWB.json"
        val FILE_PATH_MET = "$BASE_DIRECTORY/shapesMET.json"

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

        fun showNotification(context: Context,city:City, text:String, progress :Int = 0, infinityProgress: Boolean = false){
            if(city == City.CWB){
                showCustomNotification(context = context,
                        channel = getUpdateDataNotification(UpdateData.CWB_SHAPES).channel,
                        notificationId = getUpdateDataNotification(UpdateData.CWB_SHAPES).id,
                        text = text,
                        sortKey = "4",
                        progress = progress,
                        infinityProgress = infinityProgress)
            }else{
                showCustomNotification(context = context,
                        channel = getUpdateDataNotification(UpdateData.MET_SHAPES).channel,
                        notificationId = getUpdateDataNotification(UpdateData.MET_SHAPES).id,
                        text = text,
                        sortKey = "4",
                        progress = progress,
                        infinityProgress = infinityProgress)
            }
        }
    }
}