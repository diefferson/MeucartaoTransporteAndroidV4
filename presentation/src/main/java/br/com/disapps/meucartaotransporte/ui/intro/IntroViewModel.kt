package br.com.disapps.meucartaotransporte.ui.intro

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import br.com.disapps.domain.interactor.lines.InitLines
import br.com.disapps.domain.interactor.preferences.SaveIsFirstAccess
import br.com.disapps.domain.interactor.schedules.InitSchedules
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class IntroViewModel(private val initLinesUseCase: InitLines,
                     private val initSchedulesUseCase: InitSchedules,
                     private val saveIsFirstAccessUseCase: SaveIsFirstAccess) : BaseViewModel(){

    val isComplete = MutableLiveData<Boolean>().apply { value = false }
    private var linesComplete = false
    private var schedulesComplete = false

    fun initData(){
        if(!isRequested){
            isRequested = true
            initLines()
            initSchedules()
        }
    }

    private fun initLines(){
        initLinesUseCase.execute(Unit, onError = {
            Log.i("ERROR", it.message)
        }) {
            linesComplete = true
            if (schedulesComplete) {
                isComplete.value = true
                saveIsFirstAccess()
            }
        }
    }

    private fun initSchedules(){
        initSchedulesUseCase.execute(Unit, onError = {
            Log.i("ERROR", it.message)
        }) {
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
        initLinesUseCase.dispose()
        initSchedulesUseCase.dispose()
        saveIsFirstAccessUseCase.dispose()
    }
}