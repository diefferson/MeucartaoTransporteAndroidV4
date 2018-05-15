package br.com.disapps.meucartaotransporte.ui.line.itineraries

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.itineraries.GetItineraryDirections
import br.com.disapps.domain.interactor.preferences.GetIsDownloadedCwbItineraries
import br.com.disapps.domain.interactor.preferences.GetIsDownloadedMetropolitanItineraries
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ItinerariesViewModel(private val getItineraryDirectionsUseCase: GetItineraryDirections,
                           private val getIsDownloadedMetropolitanItinerariesUseCase: GetIsDownloadedMetropolitanItineraries,
                           private val getIsDownloadedCwbItinerariesUseCase: GetIsDownloadedCwbItineraries) : BaseViewModel(){

    val itineraryDirections = MutableLiveData<List<String>>()
    val isDownloaded = MutableLiveData<Boolean>()

    fun getIsDownloaded(city : City){
        when(city){
            City.CWB -> getIsDownloadedCwbItinerariesUseCase.execute(Unit){
                isDownloaded.value = it
            }
            City.MET ->getIsDownloadedMetropolitanItinerariesUseCase.execute(Unit){
                isDownloaded.value = it
            }
        }
    }

    fun getItineraryDirections(codeLine : String){
        getItineraryDirectionsUseCase.execute(GetItineraryDirections.Params(codeLine)) {
            itineraryDirections.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        getItineraryDirectionsUseCase.dispose()
        getIsDownloadedCwbItinerariesUseCase.dispose()
        getIsDownloadedMetropolitanItinerariesUseCase.dispose()
    }
}