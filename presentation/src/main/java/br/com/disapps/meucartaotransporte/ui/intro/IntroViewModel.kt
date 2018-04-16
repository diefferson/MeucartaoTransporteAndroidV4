package br.com.disapps.meucartaotransporte.ui.intro

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.interactor.lines.GetAllLinesJson
import br.com.disapps.domain.interactor.lines.SaveAllLinesJson
import br.com.disapps.domain.interactor.preferences.SaveIsFirstAccess
import br.com.disapps.domain.interactor.schedules.GetAllSchedulesJson
import br.com.disapps.domain.interactor.schedules.SaveAllSchedulesJson
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class IntroViewModel(val getAllLinesJsonUseCase: GetAllLinesJson,
                     val saveAllLinesJsonUseCase: SaveAllLinesJson,
                     val getAllSchedulesJsonUseCase: GetAllSchedulesJson,
                     val saveAllSchedulesJsonUseCase: SaveAllSchedulesJson,
                     val saveIsFirstAccessUseCase: SaveIsFirstAccess) : BaseViewModel(){


    val isComplete = MutableLiveData<Boolean>().apply { value = false }
    private var linesComplete = false
    private var schedulesComplete = false

    fun initData(){
        if(!isRequested){
            isRequested = true
            downloadLines()
            downloadSchedules()
        }
    }

    private fun downloadLines(){
        getAllLinesJsonUseCase.execute(object : DefaultSingleObserver<String>(){
            override fun onSuccess(t: String) {
                saveLines(t)
            }
        }, Unit)
    }

    private fun downloadSchedules(){
        getAllSchedulesJsonUseCase.execute(object : DefaultSingleObserver<String>(){
            override fun onSuccess(t: String) {
                saveSchedules(t)
            }
        }, Unit)
    }

    private fun saveLines(json:String){

        saveAllLinesJsonUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                linesComplete = true
                if(schedulesComplete){
                    isComplete.value = true
                }
            }
        },SaveAllLinesJson.Params(json))
    }

    private fun saveSchedules(json:String){

        saveAllSchedulesJsonUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                schedulesComplete = true
                if(linesComplete){
                    isComplete.value = true
                }
            }
        }, SaveAllSchedulesJson.Params(json))
    }

    fun saveIsFirstAccess(){
        saveIsFirstAccessUseCase.execute(object : DefaultCompletableObserver(){

        }, SaveIsFirstAccess.Params(false))
    }

    override fun onCleared() {
        super.onCleared()
        getAllLinesJsonUseCase.dispose()
        saveAllLinesJsonUseCase.dispose()
        getAllSchedulesJsonUseCase.dispose()
        saveAllSchedulesJsonUseCase.dispose()
    }
}