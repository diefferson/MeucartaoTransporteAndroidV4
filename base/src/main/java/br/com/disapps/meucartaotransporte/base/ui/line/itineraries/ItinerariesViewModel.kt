package br.com.disapps.meucartaotransporte.base.ui.line.itineraries

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.itineraries.GetItineraryDirections
import br.com.disapps.domain.interactor.preferences.GetIsDownloadedCwbItineraries
import br.com.disapps.domain.interactor.preferences.GetIsDownloadedMetropolitanItineraries
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.base.ui.common.BaseViewModel
import java.io.Serializable

/**
 * Created by dnso on 12/03/2018.
 */
class ItinerariesViewModel(private val getItineraryDirectionsUseCase: GetItineraryDirections,
                           private val getIsDownloadedMetropolitanItinerariesUseCase: GetIsDownloadedMetropolitanItineraries,
                           private val getIsDownloadedCwbItinerariesUseCase: GetIsDownloadedCwbItineraries) : BaseViewModel(), Serializable{

    val itineraryDirections = MutableLiveData<List<String>>()
    val isDownloaded = MutableLiveData<Boolean>()
    val downloadProgress = MutableLiveData<Int>().apply { value = 0 }

    val updateProgressListener  = object : DownloadProgressListener {
        override fun onAttachmentDownloadUpdate(percent: Int) {
            downloadProgress.value = percent
        }
    }

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
        getItineraryDirectionsUseCase.execute(GetItineraryDirections.Params(codeLine),onError = {
            itineraryDirections.value = ArrayList()
        }) {
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