package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.exception.UiError
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class BalanceViewModel(private val getCardUseCase: GetCard) : BaseViewModel(){

    val card = MutableLiveData<CardVO>()

    fun getCard(code: String, cpf: String, force: Boolean = false){
        if(!isRequested || force){
            loadingEvent.value = true
            isRequested = true
            getCardUseCase.execute(GetCard.Params(Card(code,cpf)),
                onSuccess = {
                    loadingEvent.value = false
                    if(it!= null) card.value = it.toCardVO()

                },
                onError ={
                    loadingEvent.value = false
                    if(it is KnownException){
                        errorEvent.value = UiError(it.knownError, it.message?:"")
                    }else{
                        errorEvent.value = UiError(KnownError.UNKNOWN_EXCEPTION,"")
                    }

                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        getCardUseCase.dispose()
    }
}