package br.com.disapps.meucartaotransporte.ui.intro

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.DefaultObserver
import br.com.disapps.domain.interactor.lines.GetAllLinesJson
import br.com.disapps.domain.interactor.lines.SaveAllLinesJson
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class IntroViewModel(val getAllLinesJsonUseCase: GetAllLinesJson,
                     val saveAllLinesJsonUseCase: SaveAllLinesJson) : BaseViewModel(){


    val isComplete = MutableLiveData<Boolean>().apply { value = false }

    fun downloadLines(){

        getAllLinesJsonUseCase.execute(object : DefaultObserver<String>(){
            override fun onError(exception: Throwable) {
                loadingEvent.value = false
            }

            override fun onNext(t: String) {
                saveLines(t)
            }

        }, Unit)

    }

    private fun saveLines(json:String){

        saveAllLinesJsonUseCase.execute(object :DefaultObserver<Boolean>(){

            override fun onError(exception: Throwable) {
                loadingEvent.value = false
            }

            override fun onNext(t: Boolean) {
                loadingEvent.value = false
                isComplete.value = true
            }

        }, SaveAllLinesJson.Params(json))
    }

}