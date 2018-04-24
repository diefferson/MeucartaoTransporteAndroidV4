package br.com.disapps.meucartaotransporte.ui.cards.registerCard

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.interactor.cards.HasCard
import br.com.disapps.domain.interactor.cards.SaveCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.model.KnownError
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.extensions.clean
import br.com.disapps.meucartaotransporte.util.extensions.isCPF

class RegisterCardViewModel(private val hasCardUseCase: HasCard,
                            private val saveCardUseCase: SaveCard,
                            private val getCardUseCase: GetCard) : BaseViewModel(){

    val code = MutableLiveData<String>()
    val cpf = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val isValidCode = MutableLiveData<Boolean>().apply { value = true }
    val isValidCpf = MutableLiveData<Boolean>().apply { value  = true }
    val isValidName = MutableLiveData<Boolean>().apply { value = true }
    val isFinished = MutableLiveData<Boolean>()

    fun consult(){

        var valid = true

        isValidCode.value = true
        isValidCpf.value = true
        isValidName.value = true

        name.value = name.value?.clean()

        if(name.value.isNullOrEmpty()){
            isValidName.value = false
            valid = false
        }

        if(code.value.isNullOrEmpty()){
            isValidCode.value = false
            valid = false
        }

        if(cpf.value.isNullOrEmpty() || !cpf.value!!.isCPF()){
            isValidCpf.value = false
            valid = false
        }

        if(valid){
            validateHasLocalCard()
        }
    }

    private fun validateHasLocalCard(){
        loadingEvent.value = true
        hasCardUseCase.execute(HasCard.Params(getFormCard()),
            onError ={
                loadingEvent.value = false
                errorEvent.value = KnownError("Erro ao verificar cartao")
            },

            onSuccess = {
                if(it){
                    loadingEvent.value = false
                    errorEvent.value = KnownError("Cartao já cadastrado")
                }else{
                    validateHasCloudCard()
                }
            }
        )
    }

    private fun validateHasCloudCard(){
        getCardUseCase.execute(GetCard.Params(getFormCard()),
            onError = {
                loadingEvent.value = false
                errorEvent.value = KnownError("Erro ao consultar cartao")
            },
            onSuccess= {
                if(it!= null){
                    it.name = name.value.toString()
                    saveCard(it)
                }else{
                    loadingEvent.value = false
                }
            }
        )
    }

    private fun saveCard(card: Card){

        saveCardUseCase.execute(SaveCard.Params(card),
                onError = {
                    loadingEvent.value = false
                }, onComplete = {
                    isFinished.value = true
                    loadingEvent.value = false
                }
        )
    }

    private fun getFormCard() : Card{
        return Card(
            code = code.value.toString(),
            cpf = cpf.value.toString(),
            name = name.value.toString()
        )
    }

    override fun onCleared() {
        super.onCleared()
        getCardUseCase.dispose()
        saveCardUseCase.dispose()
        hasCardUseCase.dispose()
    }
}