package br.com.disapps.meucartaotransporte.ui.settings.updateData

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultObserver
import br.com.disapps.domain.interactor.events.GetUpdateDataEvent
import br.com.disapps.domain.model.*
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class UpdatingDataViewModel(private val getUpdateDataEventUseCase: GetUpdateDataEvent) : BaseViewModel(){

    val events = MutableLiveData<UpdateDataEvent>()

    fun getUpdateDataEvent(updateData: UpdateData) {
        if(!isRequested){
            isRequested = true
            getUpdateDataEventUseCase.execute(object : DefaultObserver<UpdateDataEvent>(){
                override fun onNext(t: UpdateDataEvent) {
                    when(updateData){
                        UpdateData.LINES -> if(t is UpdateLinesEvent) events.value = t
                        UpdateData.SCHEDULES -> if(t is UpdateSchedulesEvent) events.value = t
                        UpdateData.CWB_ITINERARIES -> if(t is UpdateCwbItinerariesEvent) events.value = t
                        UpdateData.MET_ITINERARIES -> if(t is UpdateMetItinerariesEvent) events.value = t
                        UpdateData.CWB_SHAPES -> if(t is UpdateCwbShapesEvent) events.value = t
                        UpdateData.MET_SHAPES -> if(t is UpdateMetShapesEvent) events.value = t
                    }
                }
            }, Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        getUpdateDataEventUseCase.dispose()
    }
}