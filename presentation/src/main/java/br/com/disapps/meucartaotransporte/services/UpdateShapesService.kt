package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.domain.interactor.base.UseCaseCompletableCallback
import br.com.disapps.domain.interactor.base.UseCaseCallback
import br.com.disapps.domain.interactor.shapes.GetAllShapesJson
import br.com.disapps.domain.interactor.shapes.SaveAllShapesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.*
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import org.koin.android.ext.android.inject
import br.com.disapps.meucartaotransporte.util.showCustomNotification

class UpdateShapesService : BaseService(){

    private val getAllShapesJsonUseCase : GetAllShapesJson by inject()
    private val saveAllShapesJsonUseCase : SaveAllShapesJson by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true
            city = intent.extras.getSerializable(CITY) as City
            isManual = intent.extras.getBoolean(IS_MANUAL)

            if(isManual){
                showNotification(text =  getString(R.string.updating_shapes), infinityProgress = true )
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

            updateShapes(city)
        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        getAllShapesJsonUseCase.dispose()
        saveAllShapesJsonUseCase.dispose()
    }

    private fun updateShapes(city: City){
        getAllShapesJsonUseCase.execute(object : UseCaseCallback<String>(){
            override fun onSuccess(t: String) {
                saveShapes(t, city)
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, GetAllShapesJson.Params(city,updateProgressListener))
    }

    private fun saveShapes(json:String, city: City){

        showNotification(text = getString(R.string.saving_data), infinityProgress = true)

        saveAllShapesJsonUseCase.execute(object : UseCaseCompletableCallback(){
            override fun onComplete() {
                isComplete.value = true
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, SaveAllShapesJson.Params(json, city))
    }

    private val updateProgressListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            showNotification(text = getString(R.string.updating_shapes), progress =  percent)
        }
    }

    private fun showNotification(text:String, progress :Int = 0, infinityProgress: Boolean = false){
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
            context.startService(Intent(context, UpdateShapesService::class.java).apply {
                putExtra(IS_MANUAL, manual)
                putExtra(CITY, city)
            })
        }
    }
}