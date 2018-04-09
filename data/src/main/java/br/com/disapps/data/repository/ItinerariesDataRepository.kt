package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.ItinerariesDataSourceFactory
import br.com.disapps.domain.repository.ItinerariesRepository
import io.reactivex.Completable
import io.reactivex.Single

class ItinerariesDataRepository( private val itinerariesDataSourceFactory: ItinerariesDataSourceFactory) : ItinerariesRepository {

    override fun jsonItineraries(city: String): Single<String> {
        return itinerariesDataSourceFactory
                .create(true)
                .jsonItineraries(city)
    }

    override fun saveAllFromJson(json: String): Completable {
        return itinerariesDataSourceFactory
                .create()
                .saveAllFromJson(json)
    }
}