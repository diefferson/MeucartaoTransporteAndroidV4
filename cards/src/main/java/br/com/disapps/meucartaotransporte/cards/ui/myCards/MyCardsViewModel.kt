package br.com.disapps.meucartaotransporte.cards.ui.myCards

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.cards.DeleteCard
import br.com.disapps.domain.interactor.cards.GetCards
import br.com.disapps.meucartaotransporte.base.model.CardVO
import br.com.disapps.meucartaotransporte.base.model.mappers.toCardBO
import br.com.disapps.meucartaotransporte.base.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel

/**
 * Created by dnso on 14/03/2018.
 */
class MyCardsViewModel(private val getCardsUseCase: GetCards,
                       private val deleteCardUseCase: DeleteCard) : BaseViewModel(){

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

    fun deleteCard(card: CardVO){
        deleteCardUseCase.execute(DeleteCard.Params(card.toCardBO())){
            getCards()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getCardsUseCase.dispose()
        deleteCardUseCase.dispose()
    }
}