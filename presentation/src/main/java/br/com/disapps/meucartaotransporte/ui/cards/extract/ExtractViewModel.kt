package br.com.disapps.meucartaotransporte.ui.cards.extract

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.cards.GetExtract
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.meucartaotransporte.model.KnownError
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class ExtractViewModel(private val getExtractUseCase: GetExtract) : BaseViewModel(){

    val extract = MutableLiveData<List<Extract>>()

    fun getExtract(code: String, cpf: String) {
        if(!isRequested){
            isRequested = true
            getExtractUseCase.execute(object : DefaultSingleObserver<List<Extract>>() {
                override fun onError(e: Throwable) {
                    errorEvent.value = KnownError("Erro!")
                }

                override fun onSuccess(t: List<Extract>) {
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