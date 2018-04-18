package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.ItinerariesDataSourceFactory
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ItinerariesRepository
import io.reactivex.Completable
import io.reactivex.Single

class ItinerariesDataRepository( private val itinerariesDataSourceFactory: ItinerariesDataSourceFactory) : ItinerariesRepository {

    override fun jsonItineraries(city: City, downloadProgressListener: DownloadProgressListener): Single<String> {
        return itinerariesDataSourceFactory
                .create(true)
                .jsonItineraries(city, downloadProgressListener)
    }

    override fun saveAllFromJson(json: String, city: City): Completable {
        return itinerariesDataSourceFactory
                .create()
                .saveAllFromJson(json, city)
    }
}