package br.com.disapps.meucartaotransporte.ui.cards.extract

import android.arch.lifecycle.MutableLiveData
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
            getExtractUseCase.execute(GetExtract.Params(Card(code,cpf)),
                onError ={
                    errorEvent.value = KnownError("Erro!")
                },
                onSuccess = {
                    extract.value = it
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        getExtractUseCase.dispose()
    }
}