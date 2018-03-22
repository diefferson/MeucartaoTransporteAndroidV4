package br.com.disapps.meucartaotransporte.ui.quickFind

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import br.com.disapps.domain.interactor.DefaultObserver
import br.com.disapps.domain.interactor.cards.GetCard
import br.com.disapps.domain.model.Card
import br.com.disapps.domain.repository.CardsRepository
import br.com.disapps.meucartaotransporte.ui.cards.CardsViewModel
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.extensions.isCPF


/**
 * Created by dnso on 12/03/2018.
 */
class QuickFindViewModel : BaseViewModel(){

    val isValidCpf = MutableLiveData<Boolean>().apply { value = true }
    val isValidCode = MutableLiveData<Boolean>().apply { value = true }
    val isSuccess = MutableLiveData<Boolean>()
    val cpf = MutableLiveData<String>()
    val code = MutableLiveData<String>()

    fun consult(){

        var valid = true

        isValidCode.value = true
        isValidCpf.value = true

        if(code.value.isNullOrEmpty()){
            isValidCode.value = false
            valid = false
        }

        if(cpf.value.isNullOrEmpty() || !cpf.value!!.isCPF()){
            isValidCpf.value = false
            valid = false
        }

        if(valid){
            isSuccess.value = true
        }
    }
}