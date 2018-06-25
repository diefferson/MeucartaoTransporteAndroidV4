package br.com.disapps.meucartaotransporte.ui.cards.quickFind

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.isCPF


/**
 * Created by dnso on 12/03/2018.
 */
class QuickFindViewModel : BaseViewModel(){

    val isValidCpf = MutableLiveData<Boolean>().apply { value = true }
    val isValidCode = MutableLiveData<Boolean>().apply { value = true }
    val isSuccess = MutableLiveData<Boolean>()
    var cpf:String = ""
    var code:String = ""

    fun consult(){

        var valid = true

        isValidCode.value = true
        isValidCpf.value = true

        if(code.isEmpty()){
            isValidCode.value = false
            valid = false
        }

        if(cpf.isEmpty() || !cpf.isCPF()){
            isValidCpf.value = false
            valid = false
        }

        if(valid){
            isSuccess.value = true
        }
    }
}