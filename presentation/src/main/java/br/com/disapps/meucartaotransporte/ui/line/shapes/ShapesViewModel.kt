package br.com.disapps.meucartaotransporte.ui.line.shapes

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.buses.GetAllBuses
import br.com.disapps.domain.interactor.itineraries.GetAllItineraries
import br.com.disapps.domain.interactor.shapes.GetShapes
import br.com.disapps.domain.model.Bus
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.model.Shape
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ShapesViewModel(private val getShapesUseCase: GetShapes,
                      private val getAllItinerariesUseCase: GetAllItineraries,
                      private val getAllBusesUseCase: GetAllBuses): BaseViewModel(){

    val shapes = MutableLiveData<List<Shape>>()
    val stops = MutableLiveData<List<BusStop>>()
    val buses = MutableLiveData<List<Bus>>()

    fun init(codeLine: String){
        if(!isRequested){
            isRequested = true
            getShapes(codeLine)
            getStops(codeLine)
            getBuses(codeLine)
        }
    }

    private fun getShapes(codeLine : String){
        getShapesUseCase.execute(GetShapes.Params(codeLine)){
            shapes.value = it
        }
    }

    private fun getStops(codeLine: String){
        getAllItinerariesUseCase.execute(GetAllItineraries.Params(codeLine)) {
            stops.value = it
        }
    }

    private fun getBuses(codeLine: String){
        getAllBusesUseCase.execute(GetAllBuses.Params(codeLine)) {
            buses.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        getShapesUseCase.dispose()
        getAllItinerariesUseCase.dispose()
        getAllBusesUseCase.dispose()
    }
}