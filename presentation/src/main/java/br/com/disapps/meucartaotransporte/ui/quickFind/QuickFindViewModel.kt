package br.com.disapps.meucartaotransporte.ui.quickFind

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.extensions.isCPF


/**
 * Created by dnso on 12/03/2018.
 */
class QuickFindViewModel : BaseViewModel(){

    val isValidCpf = MutableLiveData<Boolean>().apply { value = true }
    val isValidCode = MutableLiveData<Boolean>().apply { value = true }
    val error = MutableLiveData<String>()

    fun consult(codeCard:String, cpf: String){
        isValidCode.value = true
        isValidCpf.value = true
        error.value = "error"

        if ( codeCard.isEmpty()) {
            isValidCode.value = false
        }

        if (!cpf.isCPF()) {
            isValidCpf.value = false
        }
    }
}