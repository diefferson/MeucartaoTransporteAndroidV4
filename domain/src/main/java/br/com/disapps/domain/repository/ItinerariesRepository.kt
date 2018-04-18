package br.com.disapps.domain.repository

import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single

interface ItinerariesRepository{

    fun jsonItineraries(city : City, downloadProgressListener: DownloadProgressListener) : Single<String>

    fun saveAllFromJson(json : String, city : City): Completable

    fun getItineraryDirections(codeLine:String) :Single<List<String>>

    fun getItinerary(codeLine: String, direction: String) : Single<List<BusStop>>

}