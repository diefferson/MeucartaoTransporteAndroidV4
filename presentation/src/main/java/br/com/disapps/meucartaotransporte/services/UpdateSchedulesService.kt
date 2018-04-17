package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.events.PostEvent
import br.com.disapps.domain.interactor.schedules.GetAllSchedulesJson
import br.com.disapps.domain.interactor.schedules.SaveAllSchedulesJson
import br.com.disapps.domain.model.EventStatus
import br.com.disapps.domain.model.UpdateDataEvent
import br.com.disapps.domain.model.UpdateSchedulesEvent
import br.com.disapps.meucartaotransporte.R
import org.koin.android.ext.android.inject

class UpdateSchedulesService : BaseService(){

    private val getAllSchedulesJsonUseCase : GetAllSchedulesJson by inject()
    private val saveAllSchedulesJsonUseCase : SaveAllSchedulesJson by inject()
    private val postEvent : PostEvent by inject()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        isManual = intent.extras.getBoolean(IS_MANUAL)

        if(isManual){
            postMessage(EventStatus.START,  getString(R.string.updating_schedules) )
            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        postMessage(EventStatus.COMPLETE, getString(R.string.update_schedules_success))
                    }else{
                        postMessage( EventStatus.ERROR, getString(R.string.update_schedules_error))
                    }
                }
            })
        }

        updateSchedules()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        getAllSchedulesJsonUseCase.dispose()
        saveAllSchedulesJsonUseCase.dispose()
    }

    private fun postMessage(status: EventStatus, message : String){
        postEvent.execute(object : DefaultCompletableObserver(){}, PostEvent.Params(UpdateSchedulesEvent(status, message)))
    }

    private fun updateSchedules(){
        getAllSchedulesJsonUseCase.execute(object : DefaultSingleObserver<String>(){
            override fun onSuccess(t: String) {
                saveSchedules(t)
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, Unit)
    }

    private fun saveSchedules(json:String){
        saveAllSchedulesJsonUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                isComplete.value = true
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, SaveAllSchedulesJson.Params(json))
    }

    companion object {
        private const val IS_MANUAL = "manual"
        fun startService(context: Context){
            context.startService(Intent(context, UpdateSchedulesService::class.java).apply {
                putExtra(IS_MANUAL, true)
            })
        }
    }
}