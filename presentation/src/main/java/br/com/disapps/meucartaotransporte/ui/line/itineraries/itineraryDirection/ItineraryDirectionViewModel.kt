package br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.UseCaseCallback
import br.com.disapps.domain.interactor.itineraries.GetItinerary
import br.com.disapps.domain.model.BusStop
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class ItineraryDirectionViewModel(private val getItineraryUseCase: GetItinerary) : BaseViewModel(){

    val itinerary = MutableLiveData<List<BusStop>>()

    fun getItinerary(codeLine : String, direction : String){
        getItineraryUseCase.execute(object : UseCaseCallback<List<BusStop>>(){
            override fun onSuccess(t: List<BusStop>) {
                itinerary.value = t
            }
        }, GetItinerary.Params(codeLine, direction))
    }

    override fun onCleared() {
        super.onCleared()
        getItineraryUseCase.dispose()
    }
}