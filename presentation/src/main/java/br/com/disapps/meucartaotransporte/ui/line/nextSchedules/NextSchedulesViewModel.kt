package br.com.disapps.meucartaotransporte.ui.line.nextSchedules

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.schedules.GetLineScheduleDays
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class NextSchedulesViewModel(private val getLineScheduleDaysUseCase: GetLineScheduleDays) : BaseViewModel(){

    val days = MutableLiveData<List<Int>>()

    fun getSchedulesDays(codeLine : String){
        if(!isRequested){
            isRequested = true
            getLineScheduleDaysUseCase(this,GetLineScheduleDays.Params(codeLine)).onFailure {
                days.value = ArrayList()
            }.onSuccess{
                days.value = it
            }
        }
    }
}