package br.com.disapps.meucartaotransporte.ui.quickFind

import android.arch.lifecycle.MutableLiveData
import android.view.View
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.extensions.isCPF


/**
 * Created by dnso on 12/03/2018.
 */
class QuickFindViewModel : BaseViewModel(){

    val isValidCpf = MutableLiveData<Boolean>().apply { value = true }
    val isValidCode = MutableLiveData<Boolean>().apply { value = true }
    val cpf = MutableLiveData<String>()
    val code = MutableLiveData<String>()

    fun consult(view : View){
        isValidCode.value = true
        isValidCpf.value = true

        if(code.value.isNullOrEmpty()){
            isValidCode.value = false
        }

        if(cpf.value.isNullOrEmpty() || !cpf.value!!.isCPF()){
            isValidCpf.value = false
        }
    }
}