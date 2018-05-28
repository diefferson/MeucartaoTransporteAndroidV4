package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.domain.interactor.shapes.SaveAllShapesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class UpdateShapesService : BaseService(){

    private val saveAllShapesJsonUseCase : SaveAllShapesJson by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true

            intent?.extras?.let{

                it.getSerializable(CITY)?.let {
                    city = it as City
                }?: run {
                    city = City.CWB
                }

                isManual = it.getBoolean(IS_MANUAL)

            }?: run {
                city = City.CWB
                isManual = false
            }

            if(isManual){
                showNotification(text =  getString(R.string.updating_shapes), infinityProgress = true)
                isComplete.observe(this, Observer {
                    if(it != null){
                        if(it){
                            showNotification(text =  getString(R.string.update_shapes_success))
                        }else{
                            showNotification(text =  getString(R.string.update_shapes_error))
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
        saveAllShapesJsonUseCase.dispose()
    }

    private fun saveShapes(city: City){

        saveAllShapesJsonUseCase.execute(SaveAllShapesJson.Params(cacheDir.absolutePath +"/shapes.json", city,updateProgressListener ),
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

    private val updateProgressListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            showNotification(text = getString(R.string.updating_shapes), progress =  percent)
        }
    }

    private fun showNotification(text:String,progress :Int = 0, infinityProgress: Boolean = false){
        if(city == City.CWB){
           showCustomNotification(context = this@UpdateShapesService,
                    channel = getUpdateDataNotification(UpdateData.CWB_SHAPES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.CWB_SHAPES).id,
                    text = text,
                    sortKey = "4",
                    progress = progress,
                    infinityProgress = infinityProgress)
        }else{
            showCustomNotification(context = this@UpdateShapesService,
                    channel = getUpdateDataNotification(UpdateData.MET_SHAPES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.MET_SHAPES).id,
                    text = text,
                    sortKey = "4",
                    progress = progress,
                    infinityProgress = infinityProgress)
        }
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