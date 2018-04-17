package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.events.PostEvent
import br.com.disapps.domain.interactor.lines.GetAllLinesJson
import br.com.disapps.domain.interactor.lines.SaveAllLinesJson
import br.com.disapps.domain.model.EventStatus
import br.com.disapps.domain.model.UpdateDataEvent
import br.com.disapps.domain.model.UpdateLinesEvent
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.util.showNotification
import org.koin.android.ext.android.inject

class UpdateLinesService : BaseService(){

    private val getAllLinesJsonUseCase: GetAllLinesJson by inject()
    private val saveAllLinesJsonUseCase: SaveAllLinesJson by inject()
    private val postEvent : PostEvent by inject()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        isManual = intent.extras.getBoolean(IS_MANUAL)

        if(isManual){
            postMessage(EventStatus.START,getString(R.string.updating_lines))
            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        postMessage(EventStatus.COMPLETE, getString(R.string.update_lines_success))
                    }else{
                        postMessage( EventStatus.ERROR, getString(R.string.update_lines_error))
                    }
                }
            })
        }

        updateLines()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        getAllLinesJsonUseCase.dispose()
        saveAllLinesJsonUseCase.dispose()
    }

    private fun postMessage(status: EventStatus, message : String){
        postEvent.execute(object : DefaultCompletableObserver(){}, PostEvent.Params(UpdateLinesEvent(status, message)))
    }

    private fun updateLines(){
        getAllLinesJsonUseCase.execute(object : DefaultSingleObserver<String>(){
            override fun onSuccess(t: String) {
                saveLines(t)
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        }, Unit)
    }

    private fun saveLines(json:String){
        saveAllLinesJsonUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                isComplete.value = true
            }

            override fun onError(e: Throwable) {
                isComplete.value = false
            }
        },SaveAllLinesJson.Params(json))
    }

    companion object {
        private const val IS_MANUAL = "manual"
        fun startService(context: Context){
            context.startService(Intent(context, UpdateLinesService::class.java).apply {
                putExtra(IS_MANUAL, true)
            })
        }
    }
}