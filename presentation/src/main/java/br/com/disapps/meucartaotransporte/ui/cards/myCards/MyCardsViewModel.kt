package br.com.disapps.meucartaotransporte.ui.cards.myCards

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.DefaultObserver
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
class MyCardsViewModel(val getCardsUseCase: GetCards,
                       val deleteCardUseCase: DeleteCard) : BaseViewModel(){

    val cards = MutableLiveData<List<CardVO>>()

    fun getCards(){
        loadingEvent.value = true
        getCardsUseCase.execute(object : DefaultObserver<List<Card>>() {

            override fun onError(exception: Throwable) {
                loadingEvent.value = false
            }

            override fun onNext(t: List<Card>) {
                loadingEvent.value = false
                cards.value = t.toCardVO()
            }
        }, Unit)
    }

    fun deleteCard(card: CardVO){

        deleteCardUseCase.execute(object : DefaultObserver<Boolean>(){
            override fun onError(exception: Throwable) {
                loadingEvent.value = false
            }

            override fun onNext(t: Boolean) {
                loadingEvent.value = false
                getCards()
            }

        }, DeleteCard.Params.forCard(card.toCardBO()))
    }
}