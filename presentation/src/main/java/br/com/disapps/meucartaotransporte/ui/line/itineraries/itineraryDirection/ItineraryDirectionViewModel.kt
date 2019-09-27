package br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.itineraries.GetItinerary
import br.com.disapps.domain.model.BusStop
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

class ItineraryDirectionViewModel(private val getItineraryUseCase: GetItinerary) : BaseViewModel(){

    val itinerary = MutableLiveData<List<BusStop>>()

    fun getItinerary(codeLine : String, direction : String){
        loadingEvent.value = true
        getItineraryUseCase(this, GetItinerary.Params(codeLine, direction)).onFailure {
            loadingEvent.value = false
            itinerary.value = ArrayList()
        }.onSuccess {
            loadingEvent.value = false
            itinerary.value = it
        }
    }
}