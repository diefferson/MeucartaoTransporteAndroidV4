package br.com.disapps.meucartaotransporte.base.ui.settings.dataUsage

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.preferences.GetDataUsage
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.base.util.formatDate
import java.util.*

class DataUsageViewModel(private val getDataUsageUseCase: GetDataUsage):BaseViewModel(){

    val dateLines = MutableLiveData<String>()
    val dateSchedules  = MutableLiveData<String>()
    val dateCwbItineraries  = MutableLiveData<String>()
    val dateCwbShapes  = MutableLiveData<String>()
    val dateMetItineraries  = MutableLiveData<String>()
    val dateMetShapes  = MutableLiveData<String>()

    fun init(){
        getDataUsageUseCase.execute(Unit) {

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

    override fun onCleared() {
        super.onCleared()
        getDataUsageUseCase.dispose()
    }
}