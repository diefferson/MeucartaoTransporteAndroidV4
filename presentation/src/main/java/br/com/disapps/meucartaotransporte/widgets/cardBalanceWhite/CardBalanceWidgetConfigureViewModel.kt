package br.com.disapps.meucartaotransporte.widgets.cardBalanceWhite

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.cards.GetCards
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class CardBalanceWidgetConfigureViewModel(private val getCardsUseCase: GetCards) : BaseViewModel(){

    val cards = MutableLiveData<List<CardVO>>()

    fun getCards(){
        loadingEvent.value = true
        getCardsUseCase.execute(Unit, onError = {
            loadingEvent.value = false
            cards.value = ArrayList()
        }){
            loadingEvent.value = false
            cards.value = it.toCardVO()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getCardsUseCase.dispose()
    }
}