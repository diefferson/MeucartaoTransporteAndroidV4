package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class BalanceViewModel(private val getCardUseCase: GetCard) : BaseViewModel(){

    val onError = MutableLiveData<Boolean>()
    val card = MutableLiveData<CardVO>()

    fun getCard(code: String, cpf: String){
        if(!isRequested){
            isRequested = true
            getCardUseCase.execute(GetCard.Params(Card(code,cpf)),
                onSuccess = {
                    if(it!= null) card.value = it.toCardVO()
                },
                onError ={
                    onError.value = true
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        getCardUseCase.dispose()
    }
}