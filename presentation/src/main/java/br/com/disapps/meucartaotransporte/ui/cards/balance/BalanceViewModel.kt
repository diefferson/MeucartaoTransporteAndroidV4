package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class BalanceViewModel(private val getCardUseCase: GetCard) : BaseViewModel(){

    val isSuccess = MutableLiveData<Boolean>()
    val card = MutableLiveData<CardVO>()

    fun getCard(code: String, cpf: String){
        if(!isRequested){
            isRequested = true
            loadingEvent.value = true
            getCardUseCase.execute(object : DefaultSingleObserver<Card?>(){

                override fun onError(e: Throwable) {
                    loadingEvent.value = false
                    isSuccess.value = false
                }

                override fun onSuccess(t: Card?) {
                    loadingEvent.value = false
                    isSuccess.value = true
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