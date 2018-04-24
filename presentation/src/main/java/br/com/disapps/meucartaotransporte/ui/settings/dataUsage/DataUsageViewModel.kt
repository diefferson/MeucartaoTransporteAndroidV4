package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.preferences.GetDataUsage
import br.com.disapps.domain.interactor.preferences.SavePeriodUpdateLines
import br.com.disapps.domain.interactor.preferences.SavePeriodUpdateSchedules
import br.com.disapps.domain.model.PeriodUpdate
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.formatDate
import java.util.*

class DataUsageViewModel(private val getDataUsageUseCase: GetDataUsage,
                         private val savePeriodUpdateLinesUseCase: SavePeriodUpdateLines,
                         private val savePeriodUpdateSchedulesUseCase: SavePeriodUpdateSchedules):BaseViewModel(){

    val periodLines = MutableLiveData<String>()
    val periodSchedules = MutableLiveData<String>()
    val dateLines = MutableLiveData<String>()
    val dateSchedules  = MutableLiveData<String>()
    val dateCwbItineraries  = MutableLiveData<String>()
    val dateCwbShapes  = MutableLiveData<String>()
    val dateMetItineraries  = MutableLiveData<String>()
    val dateMetShapes  = MutableLiveData<String>()

    fun init(){
        getDataUsageUseCase.execute(Unit) {
            periodLines.value = it.periodLines
            periodSchedules.value = it.periodSchedules
            dateLines.value = formatDate(Date(it.dateUpdateLines))
            dateSchedules.value = formatDate(Date(it.dateUpdateSchedules))

            if(it.dateUpdateCwbItineraries >0){
                dateCwbItineraries.value = formatDate(Date(it.dateUpdateCwbItineraries))
            }

            if(it.dateUpdateCwbShapes > 0){
                dateCwbShapes.value = formatDate(Date(it.dateUpdateCwbShapes))
            }

            if(it.dateUpdateMetItineraries > 0){
                dateMetItineraries.value = formatDate(Date(it.dateUpdateMetItineraries))
            }

            if(it.dateUpdateMetShapes > 0){
                dateMetShapes.value = formatDate(Date(it.dateUpdateMetShapes))
            }
        }
    }

    fun savePeriodUpdateLines(periodUpdate: PeriodUpdate){
        savePeriodUpdateLinesUseCase.execute(SavePeriodUpdateLines.Params(periodUpdate)){
            init()
        }
    }

    fun savePeriodUpdateSchedules(periodUpdate: PeriodUpdate){
        savePeriodUpdateSchedulesUseCase.execute(SavePeriodUpdateSchedules.Params(periodUpdate)){
            init()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getDataUsageUseCase.dispose()
        savePeriodUpdateLinesUseCase.dispose()
        savePeriodUpdateSchedulesUseCase.dispose()
    }
}