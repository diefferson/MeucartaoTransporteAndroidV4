package br.com.disapps.meucartaotransporte.ui.schedules

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.schedules.GetAllPointSchedules
import br.com.disapps.domain.model.Schedule
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class SchedulesViewModel(private val getAllPointSchedulesUseCase: GetAllPointSchedules) : BaseViewModel(){

    val schedules = MutableLiveData<List<Schedule>>()

    fun getSchedules(codeLine:String, day: Int, codePoint : String){
        if(!isRequested){
            loadingEvent.value = true
            isRequested = true

            getAllPointSchedulesUseCase(this, GetAllPointSchedules.Params(codeLine, day, codePoint)).onFailure {
                loadingEvent.value = false
                schedules.value = ArrayList()
            }.onSuccess{
                loadingEvent.value = false
                schedules.value = it
            }

        }
    }
}