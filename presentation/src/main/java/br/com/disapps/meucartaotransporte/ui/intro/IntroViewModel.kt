package br.com.disapps.meucartaotransporte.ui.intro

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultObserver
import br.com.disapps.domain.interactor.events.GetUpdateLinesEvent
import br.com.disapps.domain.interactor.events.GetUpdateSchedulesEvent
import br.com.disapps.domain.interactor.preferences.SaveIsFirstAccess
import br.com.disapps.domain.model.EventStatus
import br.com.disapps.domain.model.UpdateLinesEvent
import br.com.disapps.domain.model.UpdateSchedulesEvent
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

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
        getUpdateLinesEventUseCase.execute(object : DefaultObserver<UpdateLinesEvent>() {
            override fun onNext(t: UpdateLinesEvent) {
                if(t.status == EventStatus.COMPLETE){
                    linesComplete = true
                    if(schedulesComplete){
                        isComplete.value = true
                        saveIsFirstAccess()
                    }
                }
            }
        }, Unit)
    }

    private fun updateSchedules(){
        getUpdateSchedulesEventUseCase.execute(object : DefaultObserver<UpdateSchedulesEvent>() {
            override fun onNext(t: UpdateSchedulesEvent) {
                if(t.status == EventStatus.COMPLETE){
                    schedulesComplete = true
                    if(linesComplete){
                        isComplete.value = true
                        saveIsFirstAccess()
                    }
                }
            }
        }, Unit)
    }

    private fun saveIsFirstAccess(){
        saveIsFirstAccessUseCase.execute(object : DefaultCompletableObserver(){
        }, SaveIsFirstAccess.Params(false))
    }

    override fun onCleared() {
        super.onCleared()
        getUpdateLinesEventUseCase.dispose()
        getUpdateSchedulesEventUseCase.dispose()
        saveIsFirstAccessUseCase.dispose()
    }
}