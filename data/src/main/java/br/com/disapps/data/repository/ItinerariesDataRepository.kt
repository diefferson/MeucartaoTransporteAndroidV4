package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.ItinerariesDataSourceFactory
import br.com.disapps.data.entity.mappers.toBusStopBO
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ItinerariesRepository

class ItinerariesDataRepository( private val itinerariesDataSourceFactory: ItinerariesDataSourceFactory) : ItinerariesRepository {

    override suspend fun jsonItineraries(city: City, downloadProgressListener: DownloadProgressListener): String {
        return itinerariesDataSourceFactory
                .create(true)
                .jsonItineraries(city, downloadProgressListener)
    }

    override suspend fun saveAllFromJson(json: String, city: City){
        return itinerariesDataSourceFactory
                .create()
                .saveAllFromJson(json, city)
    }

    override suspend fun getItineraryDirections(codeLine: String): List<String> {
        return  itinerariesDataSourceFactory
                .create()
                .getItineraryDirections(codeLine)
    }

    override suspend fun getItinerary(codeLine: String, direction: String): List<BusStop> {
        return  itinerariesDataSourceFactory
                .create()
                .getItinerary(codeLine, direction)
                .map { it.toBusStopBO() }
    }

    override suspend fun getAllItineraries(codeLine: String): List<BusStop> {
        return itinerariesDataSourceFactory
                .create()
                .getAllItineraries(codeLine)
                .map { it.toBusStopBO() }
    }
}