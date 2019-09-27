package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.interactor.cards.GetPassValue
import br.com.disapps.domain.interactor.cards.UpdateCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.exception.UiException
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.model.mappers.toCardVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class BalanceViewModel(private val getCardUseCase: GetCard,
                       private val updateCard: UpdateCard,
                       private val getPassValue: GetPassValue) : BaseViewModel(){

    val card = MutableLiveData<CardVO>()
    var passValue :Float = 0f

    init {
        getPassValue(this).onSuccess{
            passValue = it
        }
    }

    fun getCard(code: String, cpf: String, force: Boolean = false){
        if(!isRequested || force){
            loadingEvent.value = true
            isRequested = true
            getCardUseCase(this, GetCard.Params(Card(code,cpf))).onFailure {
                loadingEvent.value = false
                exceptionEvent.value = if(it is KnownException){
                    UiException(it.knownError, it.message?:"")
                }else{
                    UiException(KnownError.UNKNOWN_EXCEPTION,"")
                }
            }.onSuccess {
                loadingEvent.value = false
                if(it!= null){
                    updateCard(this,UpdateCard.Params(it))
                }

                if(it!= null) card.value = it.toCardVO()
            }
        }
    }
}