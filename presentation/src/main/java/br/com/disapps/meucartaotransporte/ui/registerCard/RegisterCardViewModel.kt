package br.com.disapps.meucartaotransporte.ui.registerCard

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.DefaultObserver
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.interactor.cards.HasCard
import br.com.disapps.domain.interactor.cards.SaveCard
import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.model.KnownError
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.extensions.clean
import br.com.disapps.meucartaotransporte.util.extensions.isCPF

class RegisterCardViewModel(val hasCardUseCase: HasCard,
                            val saveCardUseCase: SaveCard,
                            val getCardUseCase: GetCard) : BaseViewModel(){

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
        hasCardUseCase.execute(object : DefaultObserver<Boolean>() {

            override fun onError(exception: Throwable) {
                loadingEvent.value = false
                errorEvent.value = KnownError("Erro ao verificar cartao")
            }

            override fun onNext(t: Boolean) {
                if(t){
                    loadingEvent.value = false
                    errorEvent.value = KnownError("Cartao já cadastrado")
                }else{
                    validateHasCloudCard()
                }
            }

        },HasCard.Params.forCard(getFormCard()))
    }

    private fun validateHasCloudCard(){
        getCardUseCase.execute(object : DefaultObserver<Card?>(){

            override fun onError(exception: Throwable) {
                loadingEvent.value = false
                errorEvent.value = KnownError("Erro ao consultar cartao")
            }

            override fun onNext(t: Card?) {
                if(t!= null){
                    t.name = name.value.toString()
                    saveCard(t)
                }else{
                    loadingEvent.value = false
                }
            }

        }, GetCard.Params.forCard(getFormCard()))
    }

    private fun saveCard(card: Card){

        saveCardUseCase.execute(object :  DefaultObserver<Boolean>() {

            override fun onError(exception: Throwable) {
                loadingEvent.value = false
                errorEvent.value = KnownError("Erro ao salvar cartao")
            }

            override fun onNext(t: Boolean) {
                loadingEvent.value = false
                if(t){
                    errorEvent.value = KnownError("Cartão salvo com sucesso")
                    isFinished.value = true;
                }else{
                    errorEvent.value = KnownError("Não foi possivel salvar cartao")
                }
            }

        },SaveCard.Params.forCard(card))
    }

    private fun getFormCard() : Card{
        return Card(
            code = code.value.toString(),
            cpf = cpf.value.toString(),
            name = name.value.toString()
        )
    }
}