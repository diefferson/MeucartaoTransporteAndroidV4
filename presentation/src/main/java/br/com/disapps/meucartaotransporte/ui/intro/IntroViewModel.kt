package br.com.disapps.meucartaotransporte.ui.intro

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.events.GetUpdateLinesEvent
import br.com.disapps.domain.interactor.events.GetUpdateSchedulesEvent
import br.com.disapps.domain.interactor.preferences.SaveIsFirstAccess
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
        getUpdateLinesEventUseCase.execute(Unit) {
            linesComplete = true
            if (schedulesComplete) {
                isComplete.value = true
                saveIsFirstAccess()
            }
        }
    }

    private fun updateSchedules(){
        getUpdateSchedulesEventUseCase.execute(Unit) {
            schedulesComplete = true
            if (linesComplete) {
                isComplete.value = true
                saveIsFirstAccess()
            }
        }
    }

    private fun saveIsFirstAccess(){
        saveIsFirstAccessUseCase.execute(SaveIsFirstAccess.Params(false))
    }

    override fun onCleared() {
        super.onCleared()
        getUpdateLinesEventUseCase.dispose()
        getUpdateSchedulesEventUseCase.dispose()
        saveIsFirstAccessUseCase.dispose()
    }
}