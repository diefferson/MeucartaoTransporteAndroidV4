package br.com.disapps.meucartaotransporte.ui.cards.myCards

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultCompletableObserver
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.cards.DeleteCard
import br.com.disapps.domain.interactor.cards.GetCards
import br.com.disapps.domain.model.Card
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
        getCardsUseCase.execute(object : DefaultSingleObserver<List<Card>>() {
            override fun onSuccess(t: List<Card>) {
                cards.value = t.toCardVO()
            }

        }, Unit)
    }

    fun deleteCard(card: CardVO){
        deleteCardUseCase.execute(object : DefaultCompletableObserver(){
            override fun onComplete() {
                getCards()
            }
        },DeleteCard.Params(card.toCardBO()))
    }

    override fun onCleared() {
        super.onCleared()
        getCardsUseCase.dispose()
        deleteCardUseCase.dispose()
    }
}