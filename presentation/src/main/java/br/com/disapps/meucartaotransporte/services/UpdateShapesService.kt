package br.com.disapps.meucartaotransporte.services

import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import br.com.disapps.domain.interactor.shapes.SaveAllShapesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.cancelNotification
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import org.koin.android.ext.android.inject

class UpdateShapesService : BaseService(){

    private val saveAllShapesJsonUseCase : SaveAllShapesJson by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true

            intent?.extras?.getSerializable(CITY)?.let {
                city = it as City
            }?: run {
                city = City.CWB
            }

            isManual = intent?.extras?.getBoolean(IS_MANUAL)?:false

            if(isManual){
                showNotification(text =  getString(R.string.updating_shapes), infinityProgress = true,
                        action = getAction(NotificationActionReceiver.CANCEL_ACTION))
                isComplete.observe(this, Observer {
                    if(it != null){
                        if(it){
                            showNotification(text =  getString(R.string.update_shapes_success),
                                    action = getAction(NotificationActionReceiver.CANCEL_ACTION))
                        }else{
                            showNotification(text =  getString(R.string.update_shapes_error),
                                    action = getAction(NotificationActionReceiver.RETRY_ACTION))
                        }
                        stopSelf()
                    }
                })
            }

            saveShapes(city)
        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelNotification(this, getUpdateDataNotification(UpdateData.MET_SHAPES).id)
        saveAllShapesJsonUseCase.dispose()
    }

    private fun saveShapes(city: City){

        saveAllShapesJsonUseCase.execute(SaveAllShapesJson.Params(cacheDir.absolutePath +"/shapes.json", city,updateProgressListener ),
            onError = {
                isComplete.value = false
            },

            onComplete = {
                isComplete.value = true
            }
        )
    }

    private val updateProgressListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            showNotification(text = getString(R.string.updating_shapes), progress =  percent, action = getAction(NotificationActionReceiver.CANCEL_ACTION))
        }
    }

    private fun showNotification(text:String,action: NotificationCompat.Action, progress :Int = 0, infinityProgress: Boolean = false){
        if(city == City.CWB){
           showCustomNotification(context = this@UpdateShapesService,
                    channel = getUpdateDataNotification(UpdateData.CWB_SHAPES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.CWB_SHAPES).id,
                    text = text,
                    sortKey = "4",
                    progress = progress,
                    infinityProgress = infinityProgress,
                   action = action)
        }else{
            showCustomNotification(context = this@UpdateShapesService,
                    channel = getUpdateDataNotification(UpdateData.MET_SHAPES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.MET_SHAPES).id,
                    text = text,
                    sortKey = "4",
                    progress = progress,
                    infinityProgress = infinityProgress,
                    action = action)
        }
    }

    fun getAction(typeAction : String) : NotificationCompat.Action {
        val intentAction = Intent(this, NotificationActionReceiver::class.java).apply {
            putExtra(NotificationActionReceiver.ACTION, typeAction)
            putExtra(NotificationActionReceiver.SERVICE, NotificationActionReceiver.SHAPE_SERVICE)
            putExtra(NotificationActionReceiver.CITY, city)
        }

        val pIntent = PendingIntent.getBroadcast(this, 1, intentAction, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Action(0, getString(R.string.cancel), pIntent)
    }

    companion object {
        private const val IS_MANUAL = "manual"
        private const val CITY = "city"
        fun startService(context: Context, city: City, manual :Boolean = true){
            try {
                context.startService(Intent(context, UpdateShapesService::class.java).apply {
                    putExtra(IS_MANUAL, manual)
                    putExtra(CITY, city)
                })
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}