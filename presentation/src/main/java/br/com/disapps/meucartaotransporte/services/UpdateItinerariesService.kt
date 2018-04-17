package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.events.PostEvent
import br.com.disapps.domain.interactor.itineraries.GetAllItinerariesJson
import br.com.disapps.domain.interactor.itineraries.SaveAllItinerariesJson
import br.com.disapps.domain.model.*
import br.com.disapps.meucartaotransporte.R
import org.koin.android.ext.android.inject

class UpdateItinerariesService : BaseService(){

    private val getAllItinerariesJsonUseCase : GetAllItinerariesJson by inject()
    private val saveAllItinerariesJsonUseCase : SaveAllItinerariesJson by inject()
    private val postEvent : PostEvent by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        city = intent.extras.getSerializable(CITY) as City
        isManual = intent.extras.getBoolean(IS_MANUAL)

        if(isManual){
            postMessage(EventStatus.START, getString(R.string.updating_itineraries) )
            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        postMessage(EventStatus.COMPLETE, getString(R.string.update_itineraries_success))
                    }else{
                        postMessage( EventStatus.ERROR, getString(R.string.update_itineraries_error))
                    }
                }
            })
        }

        updateItineraries(city)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        getAllItinerariesJsonUseCase.dispose()
        saveAllItinerariesJsonUseCase.dispose()
    }

    private fun postMessage(status: EventStatus, message : String){
        if(city == City.CWB){
            postEvent.execute(object : DefaultCompletableObserver(){}, PostEvent.Params(UpdateCwbItinerariesEvent(status, message)))
        }else{
            postEvent.execute(object : DefaultCompletableObserver(){}, PostEvent.Params(UpdateMetItinerariesEvent(status, message)))
        }
    }

    private fun updateItineraries(city: City){
        getAllItinerariesJsonUseCase.execute(object : DefaultSingleObserver<String>(){
            override fun onSuccess(t: String) {
                saveItineraries(t, city)
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, GetAllItinerariesJson.Params(city))
    }

    private fun saveItineraries(json:String, city: City){
        saveAllItinerariesJsonUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                isComplete.value = true
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, SaveAllItinerariesJson.Params(json, city))
    }

    companion object {
        private const val IS_MANUAL = "manual"
        private const val CITY = "city"
        fun startService(context: Context, city: City){
            context.startService(Intent(context, UpdateItinerariesService::class.java).apply {
                putExtra(IS_MANUAL, true)
                putExtra(CITY, city)
            })
        }
    }
}