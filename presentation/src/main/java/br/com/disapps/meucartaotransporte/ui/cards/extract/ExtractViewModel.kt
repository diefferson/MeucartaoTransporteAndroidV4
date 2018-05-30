package br.com.disapps.meucartaotransporte.ui.cards.extract

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.interactor.cards.GetExtract
import br.com.disapps.domain.interactor.cards.UpdateCard
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.model.Extract
import br.com.disapps.meucartaotransporte.exception.UiException
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class ExtractViewModel(private val getExtractUseCase: GetExtract, private val updateCard: UpdateCard) : BaseViewModel(){

    val extract = MutableLiveData<List<Extract>>()

    fun getExtract(code: String, cpf: String, force: Boolean = false) {
        if(!isRequested || force){
            loadingEvent.value = true
            isRequested = true
            getExtractUseCase.execute(GetExtract.Params(Card(code,cpf)),
                onError ={
                    loadingEvent.value = false
                    exceptionEvent.value = if(it is KnownException){
                        UiException(it.knownError, it.message?:"")
                    }else{
                        UiException(KnownError.UNKNOWN_EXCEPTION,"")
                    }
                },
                onSuccess = {
                    if(it!= null && it.isNotEmpty()){
                        val card = Card(code,cpf)
                        card.balance = it[0].balance
                        card.balanceDate = it[0].date
                        updateCard.execute(UpdateCard.Params(card))
                    }
                    loadingEvent.value = false
                    extract.value = it
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        getExtractUseCase.dispose()
        updateCard.dispose()
    }
}