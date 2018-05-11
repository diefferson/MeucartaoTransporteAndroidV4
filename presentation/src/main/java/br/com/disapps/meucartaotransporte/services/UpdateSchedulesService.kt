package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.domain.interactor.events.PostEvent
import br.com.disapps.domain.interactor.schedules.GetAllSchedulesJson
import br.com.disapps.domain.interactor.schedules.SaveAllSchedulesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.Event
import br.com.disapps.domain.model.UpdateSchedulesEventComplete
import br.com.disapps.domain.model.UpdateSchedulesEventError
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import org.koin.android.ext.android.inject

class UpdateSchedulesService : BaseService(){

    private val getAllSchedulesJsonUseCase : GetAllSchedulesJson by inject()
    private val saveAllSchedulesJsonUseCase : SaveAllSchedulesJson by inject()
    private val postEventUseCase : PostEvent by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true
            isManual = intent?.extras?.getBoolean(IS_MANUAL)?:false

            if(isManual){
                showNotification(text = getString(R.string.updating_schedules), infinityProgress = true)
                isComplete.observe(this, Observer {
                    if(it != null){
                        if(it){
                            postEvent(UpdateSchedulesEventComplete())
                            showNotification(text =  getString(R.string.update_schedules_success))
                        }else{
                            postEvent(UpdateSchedulesEventError())
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
        postEventUseCase.execute(PostEvent.Params(event))
    }

    private fun updateSchedules(){
        getAllSchedulesJsonUseCase.execute(GetAllSchedulesJson.Params(updateProgressListener),
            onSuccess = {
                saveSchedules(it)
            },
            onError = {
                isComplete.value = false
            }
        )
    }

    private fun saveSchedules(json:String){

        showNotification(text = getString(R.string.saving_data), infinityProgress = true)

        saveAllSchedulesJsonUseCase.execute(SaveAllSchedulesJson.Params(json),
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
        fun startService(context: Context, manual :Boolean = true){
            context.startService(Intent(context, UpdateSchedulesService::class.java).apply {
                putExtra(IS_MANUAL, manual)
            })
        }
    }
}