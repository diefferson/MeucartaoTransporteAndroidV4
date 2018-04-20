package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.events.PostEvent
import br.com.disapps.domain.interactor.schedules.GetAllSchedulesJson
import br.com.disapps.domain.interactor.schedules.SaveAllSchedulesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.*
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import org.koin.android.ext.android.inject

class UpdateSchedulesService : BaseService(){

    private val getAllSchedulesJsonUseCase : GetAllSchedulesJson by inject()
    private val saveAllSchedulesJsonUseCase : SaveAllSchedulesJson by inject()
    private val postEventUseCase : PostEvent by inject()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true
            isManual = intent.extras.getBoolean(IS_MANUAL)

            if(isManual){
                showNotification(text = getString(R.string.updating_schedules), infinityProgress = true)
                isComplete.observe(this, Observer {
                    if(it != null){
                        if(it){
                            postEvent(UpdateLinesEventComplete())
                            showNotification(text =  getString(R.string.update_schedules_success))
                        }else{
                            postEvent(UpdateLinesEventComplete())
                            showNotification(text =  getString(R.string.update_schedules_error))
                        }
                        stopSelf()
                    }
                })
            }

            updateSchedules()
        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        getAllSchedulesJsonUseCase.dispose()
        saveAllSchedulesJsonUseCase.dispose()
        postEventUseCase.dispose()
    }

    private fun postEvent(event: Event){
        postEventUseCase.execute(object : DefaultCompletableObserver(){
        }, PostEvent.Params(event))
    }

    private fun updateSchedules(){
        getAllSchedulesJsonUseCase.execute(object : DefaultSingleObserver<String>(){
            override fun onSuccess(t: String) {
                saveSchedules(t)
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, GetAllSchedulesJson.Params(updateProgressListener))
    }

    private fun saveSchedules(json:String){

        showNotification(text = getString(R.string.saving_data), infinityProgress = true)

        saveAllSchedulesJsonUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                isComplete.value = true
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, SaveAllSchedulesJson.Params(json))

    }

    private val updateProgressListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            showNotification(text = getString(R.string.updating_schedules), progress =  percent)
        }
    }

    private fun showNotification(text:String, progress :Int = 0, infinityProgress: Boolean = false){
        showCustomNotification(context = this@UpdateSchedulesService,
                channel = getUpdateDataNotification(UpdateData.SCHEDULES).channel,
                notificationId = getUpdateDataNotification(UpdateData.SCHEDULES).id,
                text = text,
                sortKey = "2",
                progress = progress,
                infinityProgress = infinityProgress)
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