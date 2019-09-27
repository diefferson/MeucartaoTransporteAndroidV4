package br.com.disapps.meucartaotransporte.ui.cards.registerCard

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.domain.exception.KnownException
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.interactor.cards.HasCard
import br.com.disapps.domain.interactor.cards.SaveCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.exception.UiException
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.clean
import br.com.disapps.meucartaotransporte.util.isCPF

class RegisterCardViewModel(private val hasCardUseCase: HasCard,
                            private val saveCardUseCase: SaveCard,
                            private val getCardUseCase: GetCard) : BaseViewModel(){

    var code = ""
    var cpf = ""
    var name = ""
    val isValidCode = MutableLiveData<Boolean>().apply { value = true }
    val isValidCpf = MutableLiveData<Boolean>().apply { value  = true }
    val isValidName = MutableLiveData<Boolean>().apply { value = true }
    val isFinished = MutableLiveData<Boolean>()

    fun consult(){

        var valid = true

        isValidCode.value = true
        isValidCpf.value = true
        isValidName.value = true

        name = name.clean()

        if(name.isEmpty()){
            isValidName.value = false
            valid = false
        }

        if(code.isEmpty()){
            isValidCode.value = false
            valid = false
        }

        if(cpf.isEmpty() || !cpf.isCPF()){
            isValidCpf.value = false
            valid = false
        }

        if(valid){
            validateHasCloudCard()
        }
    }

    private fun validateHasLocalCard(card : Card){
        hasCardUseCase(this, HasCard.Params(card)).onFailure {
            loadingEvent.value = false
            exceptionEvent.value = if(it is KnownException){
                UiException(it.knownError, it.message?:"")
            }else{
                UiException(KnownError.UNKNOWN_EXCEPTION,"")
            }
        }.onSuccess {
            if(it){
                loadingEvent.value = false
                exceptionEvent.value = UiException(KnownError.CARD_EXISTS_EXCEPTION, "")
            }else{
                saveCard(card)
            }
        }
    }

    private fun validateHasCloudCard(){
        loadingEvent.value = true
        getCardUseCase(this, GetCard.Params(getFormCard())).onFailure {
            loadingEvent.value = false
            exceptionEvent.value = if(it is KnownException){
                UiException(it.knownError, it.message?:"")
            }else{
                UiException(KnownError.UNKNOWN_EXCEPTION,"")
            }
        }.onSuccess{
            if(it!= null){
                it.name = name
                validateHasLocalCard(it)
            }else{
                loadingEvent.value = false
            }
        }
    }

    private fun saveCard(card: Card){

        saveCardUseCase(this, SaveCard.Params(card)).onFailure {
            loadingEvent.value = false
            exceptionEvent.value = if(it is KnownException){
                UiException(it.knownError, it.message?:"")
            }else{
                UiException(KnownError.UNKNOWN_EXCEPTION,"")
            }
        }.onSuccess {
            isFinished.value = true
            loadingEvent.value = false
        }
    }

    private fun getFormCard() : Card{
        return Card(
            code = code.toLong().toString(),
            cpf = cpf,
            name = name
        )
    }

    fun recreate(){
        isFinished.value = false
        exceptionEvent.value = null
        loadingEvent.value = false
    }
}