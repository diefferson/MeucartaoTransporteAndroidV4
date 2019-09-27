package br.com.disapps.meucartaotransporte.ui.navigation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.lines.GetLine
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.model.mappers.toLineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import com.chad.library.adapter.base.BaseViewHolder

class NavigationViewModel(val getLineUseCase: GetLine) :BaseViewModel(){

    val line = MutableLiveData<LineVO>()

    fun getLine(codeLine:String){
        getLineUseCase(this,GetLine.Params(codeLine)).onSuccess{
            line.value = it.toLineVO()
        }
    }
}