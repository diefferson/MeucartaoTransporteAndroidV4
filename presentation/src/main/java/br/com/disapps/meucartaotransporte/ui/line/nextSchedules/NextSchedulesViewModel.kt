package br.com.disapps.meucartaotransporte.ui.line.nextSchedules

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.schedules.GetLineScheduleDays
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class NextSchedulesViewModel(private val getLineScheduleDaysUseCase: GetLineScheduleDays) : BaseViewModel(){

    val days = MutableLiveData<List<Int>>()

    fun getSchedulesDays(codeLine : String){
        if(!isRequested){
            isRequested = true
            getLineScheduleDaysUseCase.execute(GetLineScheduleDays.Params(codeLine)) {
                days.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getLineScheduleDaysUseCase.dispose()
    }
}