package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.DefaultObserver
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class BalanceViewModel(val getCardUseCase: GetCard) : BaseViewModel(){

    private var isRequested  = false
    val isSuccess = MutableLiveData<Boolean>()
    val card = MutableLiveData<CardVO>()

    fun getCard(code: String, cpf: String){
        if(!isRequested){
            isRequested = true
            loadingEvent.value = true
            getCardUseCase.execute(GetCardObservable(), GetCard.Params.forCard(Card(code = code, cpf = cpf)))
        }
    }

    private inner class GetCardObservable : DefaultObserver<Card?>() {

        override fun onError(exception: Throwable) {
            loadingEvent.value = false
            isSuccess.value = false
        }

        override fun onNext(t: Card?) {
            loadingEvent.value = false
            isSuccess.value = true
            if(t!= null)
                card.value = t.toCardVO()
        }
    }
}