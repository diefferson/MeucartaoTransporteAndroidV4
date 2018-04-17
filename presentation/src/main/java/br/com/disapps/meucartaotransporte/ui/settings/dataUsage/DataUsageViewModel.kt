package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.DefaultSingleObserver
import br.com.disapps.domain.interactor.preferences.GetDataUsage
import br.com.disapps.domain.model.DataUsage
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import br.com.disapps.meucartaotransporte.util.formatDate
import java.util.*

class DataUsageViewModel(private val getDataUsageUseCase: GetDataUsage):BaseViewModel(){

    val periodLines = MutableLiveData<String>()
    val periodSchedules = MutableLiveData<String>()
    val dateLines = MutableLiveData<String>()
    val dateSchedules  = MutableLiveData<String>()
    val dateCwbItineraries  = MutableLiveData<String>()
    val dateCwbShapes  = MutableLiveData<String>()
    val dateMetItineraries  = MutableLiveData<String>()
    val dateMetShapes  = MutableLiveData<String>()

    fun init(){
        getDataUsageUseCase.execute(object : DefaultSingleObserver<DataUsage>(){
            override fun onSuccess(t: DataUsage) {
                periodLines.value = t.periodLines
                periodSchedules.value = t.periodSchedules
                dateLines.value = formatDate(Date(t.dateUpdateLines))
                dateSchedules.value = formatDate(Date(t.dateUpdateSchedules))

                if(t.dateUpdateCwbItineraries >0){
                    dateCwbItineraries.value = formatDate(Date(t.dateUpdateCwbItineraries))
                }

                if(t.dateUpdateCwbShapes > 0){
                    dateCwbShapes.value = formatDate(Date(t.dateUpdateCwbShapes))
                }

                if(t.dateUpdateMetItineraries > 0){
                    dateMetItineraries.value = formatDate(Date(t.dateUpdateMetItineraries))
                }

                if(t.dateUpdateMetShapes > 0){
                    dateMetShapes.value = formatDate(Date(t.dateUpdateMetShapes))
                }
            }

        }, Unit)
    }

    override fun onCleared() {
        super.onCleared()
        getDataUsageUseCase.dispose()
    }
}