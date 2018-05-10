package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class BalanceViewModel(private val getCardUseCase: GetCard) : BaseViewModel(){

    val card = MutableLiveData<CardVO>()

    fun getCard(code: String, cpf: String){
        if(!isRequested){
            isRequested = true
            getCardUseCase.execute(GetCard.Params(Card(code,cpf)),
                onSuccess = {
                    if(it!= null) card.value = it.toCardVO()
                },
                onError ={
                    if(it is KnownException){
                        errorEvent.value = it.knownError
                    }else{
                        errorEvent.value = KnownError.UNKNOWN_EXCEPTION
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