package br.com.disapps.meucartaotransporte.ui.schedules

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.schedules.GetAllPointSchedules
import br.com.disapps.domain.model.Schedule
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class SchedulesViewModel(private val getAllPointSchedulesUseCase: GetAllPointSchedules) : BaseViewModel(){

    val schedules = MutableLiveData<List<Schedule>>()
    private var isRequested = false

    fun getSchedules(codeLine:String, day: Int, codePoint : String){
        if(!isRequested){
            isRequested = true

            getAllPointSchedulesUseCase.execute(object : DefaultSingleObserver<List<Schedule>>(){
                override fun onSuccess(t: List<Schedule>) {
                    schedules.value = t
                }
            }, GetAllPointSchedules.Params(codeLine, day, codePoint))
        }
    }
}