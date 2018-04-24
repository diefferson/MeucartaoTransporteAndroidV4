package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.domain.interactor.base.UseCaseCompletableCallback
import br.com.disapps.domain.interactor.base.UseCaseCallback
import br.com.disapps.domain.interactor.events.PostEvent
import br.com.disapps.domain.interactor.lines.GetAllLinesJson
import br.com.disapps.domain.interactor.lines.SaveAllLinesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.*
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import org.koin.android.ext.android.inject

class UpdateLinesService : BaseService(){

    private val getAllLinesJsonUseCase: GetAllLinesJson by inject()
    private val saveAllLinesJsonUseCase: SaveAllLinesJson by inject()
    private val postEventUseCase : PostEvent by inject()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true
            isManual = intent.extras.getBoolean(IS_MANUAL)

            if(isManual){
                showNotification(text =getString(R.string.updating_lines), infinityProgress = true)
                isComplete.observe(this, Observer {
                    if(it != null){
                        if(it){
                            postEvent(UpdateLinesEventComplete())
                            showNotification(text = getString(R.string.update_lines_success))
                        }else{
                            postEvent(UpdateLinesEventError())
                            showNotification(text = getString(R.string.update_lines_error))
                        }
                        stopSelf()
                    }
                })
            }

            updateLines()

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        getAllLinesJsonUseCase.dispose()
        saveAllLinesJsonUseCase.dispose()
        postEventUseCase.dispose()
    }

    private fun postEvent(event: Event ){
        postEventUseCase.execute(object : UseCaseCompletableCallback(){
        }, PostEvent.Params(event))
    }

    private fun updateLines(){
        getAllLinesJsonUseCase.execute(object : UseCaseCallback<String>(){
            override fun onSuccess(t: String) {
                saveLines(t)
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, GetAllLinesJson.Params(updateProgressListener))
    }

    private fun saveLines(json:String){

        showNotification(text = getString(R.string.saving_data), infinityProgress = true)

        saveAllLinesJsonUseCase.execute(object : UseCaseCompletableCallback(){
            override fun onComplete() {
                isComplete.value = true
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        },SaveAllLinesJson.Params(json))
    }

    private val updateProgressListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            showNotification(text = getString(R.string.updating_lines), progress =  percent)
        }
    }

    private fun showNotification(text:String, progress :Int = 0, infinityProgress: Boolean = false){
        showCustomNotification(context = this@UpdateLinesService,
                channel = getUpdateDataNotification(UpdateData.LINES).channel,
                notificationId = getUpdateDataNotification(UpdateData.LINES).id,
                text = text,
                sortKey = "1",
                progress = progress,
                infinityProgress = infinityProgress)
    }

    companion object {
        private const val IS_MANUAL = "manual"
        fun startService(context: Context, manual : Boolean = true){
            context.startService(Intent(context, UpdateLinesService::class.java).apply {
                putExtra(IS_MANUAL, manual)
            })
        }
    }
}