package br.com.disapps.meucartaotransporte.ui.cards.myCards

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.cards.DeleteCard
import br.com.disapps.domain.interactor.cards.GetCards
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardBO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 14/03/2018.
 */
class MyCardsViewModel(private val getCardsUseCase: GetCards,
                       private val deleteCardUseCase: DeleteCard) : BaseViewModel(){

    val cards = MutableLiveData<List<CardVO>>()

    fun getCards(){
        loadingEvent.value = true
        getCardsUseCase(this).onFailure {
            loadingEvent.value = false
            cards.value = ArrayList()
        }.onSuccess{
            loadingEvent.value = false
            cards.value = it.toCardVO()
        }
    }

    fun deleteCard(card: CardVO){
        deleteCardUseCase(this, DeleteCard.Params(card.toCardBO())).onSuccess{
            getCards()
        }
    }
}