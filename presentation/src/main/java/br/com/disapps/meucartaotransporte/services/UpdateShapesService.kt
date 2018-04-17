package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.events.PostEvent
import br.com.disapps.domain.interactor.shapes.GetAllShapesJson
import br.com.disapps.domain.interactor.shapes.SaveAllShapesJson
import br.com.disapps.domain.model.*
import br.com.disapps.meucartaotransporte.R
import org.koin.android.ext.android.inject

class UpdateShapesService : BaseService(){

    private val getAllShapesJsonUseCase : GetAllShapesJson by inject()
    private val saveAllShapesJsonUseCase : SaveAllShapesJson by inject()
    private val postEvent : PostEvent by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        city = intent.extras.getSerializable(CITY) as City
        isManual = intent.extras.getBoolean(IS_MANUAL)

        if(isManual){
            postMessage(EventStatus.START,  getString(R.string.updating_shapes) )
            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        postMessage(EventStatus.COMPLETE,  getString(R.string.update_shapes_success))
                    }else{
                        postMessage( EventStatus.ERROR,  getString(R.string.update_shapes_error))
                    }
                }
            })
        }

        updateShapes(city)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        getAllShapesJsonUseCase.dispose()
        saveAllShapesJsonUseCase.dispose()
    }

    private fun postMessage(status: EventStatus, message : String){
        if(city == City.CWB){
            postEvent.execute(object : DefaultCompletableObserver(){}, PostEvent.Params(UpdateCwbShapesEvent(status, message)))
        }else{
            postEvent.execute(object : DefaultCompletableObserver(){}, PostEvent.Params(UpdateMetShapesEvent(status, message)))
        }
    }

    private fun updateShapes(city: City){
        getAllShapesJsonUseCase.execute(object : DefaultSingleObserver<String>(){
            override fun onSuccess(t: String) {
                saveShapes(t, city)
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, GetAllShapesJson.Params(city))
    }

    private fun saveShapes(json:String, city: City){
        saveAllShapesJsonUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                isComplete.value = true
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, SaveAllShapesJson.Params(json, city))
    }

    companion object {
        private const val IS_MANUAL = "manual"
        private const val CITY = "city"
        fun startService(context: Context, city: City){
            context.startService(Intent(context, UpdateShapesService::class.java).apply {
                putExtra(IS_MANUAL, true)
                putExtra(CITY, city)
            })
        }
    }
}