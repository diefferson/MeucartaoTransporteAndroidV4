package br.com.disapps.meucartaotransporte.ui.line.nextSchedules

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.schedules.GetLineScheduleDays
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class NextSchedulesViewModel(val getLineScheduleDaysUseCase: GetLineScheduleDays) : BaseViewModel(){

    val days = MutableLiveData<List<Int>>()
    private var isRequested = false

    fun getSchedulesDays(codeLine : String){
        if(!isRequested){
            isRequested = true
            getLineScheduleDaysUseCase.execute(object : DefaultSingleObserver<List<Int>>(){
                override fun onSuccess(t: List<Int>) {
                    days.value = t
                }
            }, GetLineScheduleDays.Params(codeLine))
        }
    }
}