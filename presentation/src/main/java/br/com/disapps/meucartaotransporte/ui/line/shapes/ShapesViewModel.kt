package br.com.disapps.meucartaotransporte.ui.line.shapes

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.buses.GetAllBuses
import br.com.disapps.domain.interactor.itineraries.GetAllItineraries
import br.com.disapps.domain.interactor.preferences.GetIsDownloadedCwbShapes
import br.com.disapps.domain.interactor.preferences.GetIsDownloadedMetropolitanShapes
import br.com.disapps.domain.interactor.shapes.GetShapes
import br.com.disapps.domain.model.Bus
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.model.City
import br.com.disapps.domain.model.Shape
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by dnso on 12/03/2018.
 */
class ShapesViewModel(private val getShapesUseCase: GetShapes,
                      private val getAllItinerariesUseCase: GetAllItineraries,
                      private val getAllBusesUseCase: GetAllBuses,
                      private val getIsDownloadedCwbShapesUseCase: GetIsDownloadedCwbShapes,
                      private val getIsDownloadedMetropolitanShapesUseCase: GetIsDownloadedMetropolitanShapes): BaseViewModel(){

    val shapes = MutableLiveData<List<Shape>>()
    val stops = MutableLiveData<List<BusStop>>()
    val buses = MutableLiveData<List<Bus>>()
    val isDownloaded = MutableLiveData<Boolean>()
    val errorViewBuses = MutableLiveData<Boolean>()
    var showStops = false
    var isPermission  = false

    fun getIsDownloaded(city: City){
        when(city){
            City.CWB -> getIsDownloadedCwbShapesUseCase(this).onSuccess{
                isDownloaded.value = it
            }
            City.MET -> getIsDownloadedMetropolitanShapesUseCase(this).onSuccess{
                isDownloaded.value = it
            }
        }
    }

    fun init(codeLine: String){
        if(!isRequested){
            isRequested = true
            getShapes(codeLine)
            getStops(codeLine)
            getBuses(codeLine)
        }
    }

    private fun getShapes(codeLine : String){
        getShapesUseCase(this,GetShapes.Params(codeLine)).onFailure {
            shapes.value = ArrayList()
        }.onSuccess{
            shapes.value = it
        }
    }

    private fun getStops(codeLine: String){
        getAllItinerariesUseCase(this,GetAllItineraries.Params(codeLine)).onSuccess {
            stops.value = it
        }
    }

    private fun getBuses(codeLine: String){
        launch {
            do {
                getAllBusesUseCase(this, GetAllBuses.Params(codeLine)).onFailure {
                    errorViewBuses.value= true
                }.onSuccess{
                    buses.value = it
                }
                delay(30000)
            }while (true)
        }
    }
}