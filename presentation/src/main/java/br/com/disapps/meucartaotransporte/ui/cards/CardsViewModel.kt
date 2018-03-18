package br.com.disapps.meucartaotransporte.ui.cards

import android.util.Log
import br.com.disapps.domain.interactor.DefaultObserver
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class CardsViewModel(var getCardUseCase: GetCard) : BaseViewModel(){

    fun getCards(){
       // getCardUseCase.execute(GetCardObservable(), GetCard.Params.forCard("25"))
    }

    private inner class GetCardObservable : DefaultObserver<Card>() {

        override fun onComplete() {
            Log.i(CardsViewModel::class.java.simpleName, "onComplete")
        }

        override fun onError(exception: Throwable) {
            Log.i(CardsViewModel::class.java.simpleName, "onError")
        }

        override fun onNext(t: Card) {
            Log.i(CardsViewModel::class.java.simpleName, "onNext")
        }
    }
}