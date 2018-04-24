package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.UseCaseCallback
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
            getCardUseCase.execute(object : UseCaseCallback<Card?>(){

                override fun onError(e: Throwable) {
                    onError.value = true
                }

                override fun onSuccess(t: Card?) {
                    if(t!= null) card.value = t.toCardVO()
                }

            }, GetCard.Params(Card(code,cpf)))
        }
    }

    override fun onCleared() {
        super.onCleared()
        getCardUseCase.dispose()
    }
}