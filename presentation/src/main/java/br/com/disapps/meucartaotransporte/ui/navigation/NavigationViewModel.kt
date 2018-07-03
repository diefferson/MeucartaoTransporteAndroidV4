package br.com.disapps.meucartaotransporte.ui.navigation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.disapps.domain.interactor.lines.GetLine
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.model.mappers.toLineVO

class NavigationViewModel(val getLineUseCase: GetLine) :ViewModel(){

    val line = MutableLiveData<LineVO>()

    fun getLine(codeLine:String){
        getLineUseCase.execute(GetLine.Params(codeLine)){
            line.value = it.toLineVO()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getLineUseCase.dispose()
    }
}