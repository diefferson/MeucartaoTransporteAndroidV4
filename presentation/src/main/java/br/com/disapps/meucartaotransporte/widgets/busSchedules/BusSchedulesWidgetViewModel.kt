package br.com.disapps.meucartaotransporte.widgets.busSchedules

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.lines.GetLines
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.model.mappers.toLineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class BusSchedulesWidgetViewModel(private val getLinesUseCase: GetLines) : BaseViewModel(){

    val lines = MutableLiveData<List<LineVO>>()

    fun getLines(){
        loadingEvent.value = true
        getLinesUseCase.execute(Unit, onError = {
            loadingEvent.value = false
        }) {
            loadingEvent.value = false
            lines.value = it.toLineVO()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getLinesUseCase.dispose()
    }
}