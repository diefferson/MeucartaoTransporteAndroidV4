package br.com.disapps.meucartaotransporte.ui.intro

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.UseCaseCompletableCallback
import br.com.disapps.domain.interactor.base.UseCaseCallback
import br.com.disapps.domain.interactor.events.GetUpdateLinesEvent
import br.com.disapps.domain.interactor.events.GetUpdateSchedulesEvent
import br.com.disapps.domain.interactor.preferences.SaveIsFirstAccess
import br.com.disapps.domain.model.UpdateLinesEventComplete
import br.com.disapps.domain.model.UpdateSchedulesEventComplete
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import kotlinx.coroutines.experimental.channels.ReceiveChannel

class IntroViewModel(private val getUpdateLinesEventUseCase: GetUpdateLinesEvent,
                     private val getUpdateSchedulesEventUseCase: GetUpdateSchedulesEvent,
                     private val saveIsFirstAccessUseCase: SaveIsFirstAccess) : BaseViewModel(){

    val isComplete = MutableLiveData<Boolean>().apply { value = false }
    private var linesComplete = false
    private var schedulesComplete = false

    fun initData(){
        if(!isRequested){
            isRequested = true
            updateLines()
            updateSchedules()
        }
    }

    private fun updateLines(){
        getUpdateLinesEventUseCase.execute(object : UseCaseCallback<UpdateLinesEventComplete>() {
            override fun onSuccess(t: UpdateLinesEventComplete) {
                linesComplete = true
                if (schedulesComplete) {
                    isComplete.value = true
                    saveIsFirstAccess()
                }
            }
        }, Unit)
    }

    private fun updateSchedules(){
        getUpdateSchedulesEventUseCase.execute(object : UseCaseCallback<UpdateSchedulesEventComplete>() {
            override fun onSuccess(t: UpdateSchedulesEventComplete) {
                schedulesComplete = true
                if (linesComplete) {
                    isComplete.value = true
                    saveIsFirstAccess()
                }
            }
        }, Unit)
    }

    private fun saveIsFirstAccess(){
        saveIsFirstAccessUseCase.execute(object : UseCaseCompletableCallback(){
        }, SaveIsFirstAccess.Params(false))
    }

    override fun onCleared() {
        super.onCleared()
        getUpdateLinesEventUseCase.dispose()
        getUpdateSchedulesEventUseCase.dispose()
        saveIsFirstAccessUseCase.dispose()
    }
}