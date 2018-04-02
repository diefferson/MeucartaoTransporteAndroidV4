package br.com.disapps.meucartaotransporte.ui.cards.extract

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.cards.GetExtract
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.meucartaotransporte.model.KnownError
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel


class ExtractViewModel(val getExtractUseCase: GetExtract) : BaseViewModel(){

    private var isRequested  = false
    val extract = MutableLiveData<List<Extract>>()

    fun getExtract(code: String, cpf: String) {
        if(!isRequested){
            isRequested = true
            loadingEvent.value = true
            getExtractUseCase.execute(object : DefaultSingleObserver<List<Extract>>() {

                override fun onError(e: Throwable) {
                    loadingEvent.value = false
                    errorEvent.value = KnownError("Erro!")
                }

                override fun onSuccess(t: List<Extract>) {
                    loadingEvent.value = false
                    extract.value = t
                }
            }, GetExtract.Params(Card(code,cpf)))
        }
    }

    override fun onCleared() {
        super.onCleared()
        getExtractUseCase.dispose()
    }
}