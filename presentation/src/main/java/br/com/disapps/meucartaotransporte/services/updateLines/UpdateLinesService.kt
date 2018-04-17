package br.com.disapps.meucartaotransporte.services.updateLines

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.lines.GetAllLinesJson
import br.com.disapps.domain.interactor.lines.SaveAllLinesJson
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.services.BaseService
import br.com.disapps.meucartaotransporte.util.showNotification
import org.koin.android.ext.android.inject

class UpdateLinesService : BaseService(){

    private val getAllLinesJsonUseCase: GetAllLinesJson by inject()
    private val saveAllLinesJsonUseCase: SaveAllLinesJson by inject()
    private val isComplete = MutableLiveData<Boolean>()
    private var manual = false

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        intent.extras?.getBoolean(IS_MANUAL, false)?.let{
            manual = it
        }

        if(manual){
            showNotification(this, NOTIFICATION_CHANEL,  NOTIFICATION_ID, getString(R.string.updating_lines) )
        }

        updateLines()

        if(manual){
            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        showNotification(this, NOTIFICATION_CHANEL, NOTIFICATION_ID, getString(R.string.update_lines_success))
                    }else{
                        showNotification(this, NOTIFICATION_CHANEL, NOTIFICATION_ID, getString(R.string.update_lines_error))
                    }
                }
            })
        }

        return super.onStartCommand(intent, flags, startId)
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
        private const val NOTIFICATION_ID = 853
        private const val NOTIFICATION_CHANEL = "UPDATES"
        fun startService(context: Context){
            context.startService(Intent(context, UpdateLinesService::class.java).apply {
                putExtra(IS_MANUAL, true)
            })
        }
    }
}