package br.com.disapps.meucartaotransporte.ui.myCards

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.DefaultObserver
import br.com.disapps.domain.interactor.cards.DeleteCard
import br.com.disapps.domain.interactor.cards.GetCards
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.CardVOMapper
import br.com.disapps.meucartaotransporte.model.mappers.Mapper
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 14/03/2018.
 */
class MyCardsViewModel(val getCardsUseCase: GetCards,
                       val deleteCardUseCase: DeleteCard) : BaseViewModel(){

    private var isRequested  = false
    val cards = MutableLiveData<List<CardVO>>()

    fun getCards(){
        if(!isRequested){
            isRequested = true
            loadingEvent.value = true
            getCardsUseCase.execute(object : DefaultObserver<List<Card>>() {

                override fun onError(exception: Throwable) {
                    loadingEvent.value = false
                }

                override fun onNext(t: List<Card>) {
                    loadingEvent.value = false
                    cards.value = CardVOMapper.mapToView(t)
                }
            }, Unit)
        }
    }

    fun deleteCard(card: CardVO){

        deleteCardUseCase.execute(object : DefaultObserver<Boolean>(){
            override fun onError(exception: Throwable) {
                loadingEvent.value = false
            }

            override fun onNext(t: Boolean) {
                isRequested = false
                loadingEvent.value = false
                getCards()
            }

        }, DeleteCard.Params.forCard(CardVOMapper.mapFromView(card)))
    }
}