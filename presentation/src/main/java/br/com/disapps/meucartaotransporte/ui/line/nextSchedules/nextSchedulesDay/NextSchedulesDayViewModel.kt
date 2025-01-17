package br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.schedules.GetLineSchedules
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.model.Schedule
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.validateSchedule

class NextSchedulesDayViewModel(private val getLineSchedulesUseCase: GetLineSchedules) : BaseViewModel(){

    val nextSchedules = MutableLiveData<List<LineSchedule>>()

    fun getNextSchedules(codeLine:String, day:Int){
        if(!isRequested){
            isRequested = true
            loadingEvent.value = true

            getLineSchedulesUseCase.execute(GetLineSchedules.Params(codeLine, day), onError = {
                nextSchedules.value = ArrayList()
                loadingEvent.value = false
            }){
                nextSchedules.value = setupNextSchedules(it)
                loadingEvent.value = false
            }
        }
    }

    private fun setupNextSchedules(t: List<LineSchedule>) : List<LineSchedule> {
        return t.map{ schedules->
            schedules.nextSchedules = filterSchedules(schedules.schedules, schedules.day)
            schedules
        }
    }

    private fun filterSchedules(schedules: List<Schedule>, day: Int) : List<Schedule>{
        return schedules.filter { validateSchedule(it, day) }.take(3)
    }

    override fun onCleared() {
        super.onCleared()
        getLineSchedulesUseCase.dispose()
    }
}