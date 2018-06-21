package br.com.disapps.meucartaotransporte.base.ui.cards.quickFind

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.base.util.isCPF


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