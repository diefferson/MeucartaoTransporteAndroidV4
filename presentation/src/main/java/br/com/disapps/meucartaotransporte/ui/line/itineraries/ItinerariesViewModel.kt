package br.com.disapps.meucartaotransporte.ui.line.itineraries

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.itineraries.GetItineraryDirections
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ItinerariesViewModel(private val getItineraryDirectionsUseCase: GetItineraryDirections) : BaseViewModel(){

    val itineraryDirections = MutableLiveData<List<String>>()

    fun getItineraryDirections(codeLine : String){
        getItineraryDirectionsUseCase.execute(GetItineraryDirections.Params(codeLine)) {
            itineraryDirections.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        getItineraryDirectionsUseCase.dispose()
    }
}