package br.com.disapps.meucartaotransporte.ui.cards.myCards

import android.arch.lifecycle.MutableLiveData
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
        getCardsUseCase.execute(Unit){
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