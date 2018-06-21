package br.com.disapps.meucartaotransporte.base.ui.line.itineraries.itineraryDirection

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.itineraries.GetItinerary
import br.com.disapps.domain.model.BusStop
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel

class ItineraryDirectionViewModel(private val getItineraryUseCase: GetItinerary) : BaseViewModel(){

    val itinerary = MutableLiveData<List<BusStop>>()

    fun getItinerary(codeLine : String, direction : String){
        loadingEvent.value = true
        getItineraryUseCase.execute(GetItinerary.Params(codeLine, direction), onError = {
            loadingEvent.value = false
            itinerary.value = ArrayList()
        }) {
            loadingEvent.value = false
            itinerary.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        getItineraryUseCase.dispose()
    }
}