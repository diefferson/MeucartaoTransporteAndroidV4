package br.com.disapps.meucartaotransporte.ui.line.itineraries

import android.arch.lifecycle.MutableLiveData
import br.com.disapps.domain.interactor.base.onFailure
import br.com.disapps.domain.interactor.base.onSuccess
import br.com.disapps.domain.interactor.itineraries.GetItineraryDirections
import br.com.disapps.domain.interactor.preferences.GetIsDownloadedCwbItineraries
import br.com.disapps.domain.interactor.preferences.GetIsDownloadedMetropolitanItineraries
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.ui.common.BaseViewModel
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
            City.CWB -> getIsDownloadedCwbItinerariesUseCase(this).onSuccess{
                isDownloaded.value = it
            }
            City.MET ->getIsDownloadedMetropolitanItinerariesUseCase(this).onSuccess{
                isDownloaded.value = it
            }
        }
    }

    fun getItineraryDirections(codeLine : String){
        getItineraryDirectionsUseCase(this, GetItineraryDirections.Params(codeLine)).onFailure {
            itineraryDirections.value = ArrayList()
        }.onSuccess {
            itineraryDirections.value = it
        }
    }
}