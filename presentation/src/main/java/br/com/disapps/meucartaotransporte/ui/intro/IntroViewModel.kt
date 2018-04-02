package br.com.disapps.meucartaotransporte.ui.intro

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.lines.GetAllLinesJson
import br.com.disapps.domain.interactor.lines.SaveAllLinesJson
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class IntroViewModel(val getAllLinesJsonUseCase: GetAllLinesJson,
                     val saveAllLinesJsonUseCase: SaveAllLinesJson) : BaseViewModel(){


    val isComplete = MutableLiveData<Boolean>().apply { value = false }

    fun downloadLines(){

        getAllLinesJsonUseCase.execute(object : DefaultSingleObserver<String>(){
            override fun onError(e: Throwable) {
                loadingEvent.value = false
            }

            override fun onSuccess(t: String) {
                saveLines(t)
            }

        }, Unit)

    }

    private fun saveLines(json:String){

        saveAllLinesJsonUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                loadingEvent.value = false
                isComplete.value = true
            }

            override fun onError(e: Throwable) {
                loadingEvent.value = false
            }
        },SaveAllLinesJson.Params(json))
    }

    override fun onCleared() {
        super.onCleared()
        getAllLinesJsonUseCase.dispose()
        saveAllLinesJsonUseCase.dispose()
    }
}