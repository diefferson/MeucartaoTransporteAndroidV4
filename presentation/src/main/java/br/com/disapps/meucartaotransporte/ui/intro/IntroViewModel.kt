package br.com.disapps.meucartaotransporte.ui.intro

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import br.com.disapps.domain.interactor.lines.SaveAllLinesJson
import br.com.disapps.domain.interactor.preferences.SaveIsFirstAccess
import br.com.disapps.domain.interactor.schedules.SaveAllSchedulesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class IntroViewModel(private val saveAllLinesJsonUseCase: SaveAllLinesJson,
                     private val saveAllSchedulesJsonUseCase: SaveAllSchedulesJson,
                     private val saveIsFirstAccessUseCase: SaveIsFirstAccess) : BaseViewModel(){

    val isComplete = MutableLiveData<Boolean>().apply { value = false }
    val progress = MutableLiveData<Int>().apply { value = 0 }
    private var linesComplete = false
    private var schedulesComplete = false
    private var progressLine = 0
    private var  progressSchedule = 0

    fun initData(cacheDir :String){
        if(!isRequested){
            isRequested = true
            initLines(cacheDir)
            initSchedules(cacheDir)
        }
    }

    private fun setProgressLines(prog:Int){
        if(prog != progressLine){
            progressLine = prog
            progress.value = progressLine+progressSchedule
        }
    }

    private fun setProgressSchedules(prog:Int){
        if(prog != progressSchedule){
            progressSchedule = prog
            progress.value = progressLine+progressSchedule
        }
    }

    private val updateProgressLinesListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            setProgressLines(percent)
        }
    }

    private val updateProgressSchedulesListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            setProgressSchedules(percent)
        }
    }

    private fun initLines(cacheDir :String){
        saveAllLinesJsonUseCase.execute(SaveAllLinesJson.Params("$cacheDir/lines.json",updateProgressLinesListener ), onError = {
            Log.i("ERROR", it.message)
        }) {
            linesComplete = true
            if (schedulesComplete) {
                isComplete.value = true
                saveIsFirstAccess()
            }
        }
    }

    private fun initSchedules(cacheDir :String){
        saveAllSchedulesJsonUseCase.execute(SaveAllSchedulesJson.Params("$cacheDir/schedules.json", updateProgressSchedulesListener), onError = {
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
        saveAllLinesJsonUseCase.dispose()
        saveAllSchedulesJsonUseCase.dispose()
        saveIsFirstAccessUseCase.dispose()
    }
}