package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Ponto
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single

interface ItinerariesDataSource : DataSource{

    fun saveAllFromJson(json : String, city: City): Completable

    fun jsonItineraries(city: City, downloadProgressListener: DownloadProgressListener) : Single<String>

    fun getItineraryDirections(codeLine:String) :Single<List<String>>

    fun getItinerary(codeLine: String, direction: String) : Single<List<Ponto>>

}