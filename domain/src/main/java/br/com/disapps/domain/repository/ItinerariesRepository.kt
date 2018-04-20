package br.com.disapps.domain.repository

import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.model.City

interface ItinerariesRepository{

    suspend fun jsonItineraries(city : City, downloadProgressListener: DownloadProgressListener) : String

    suspend fun saveAllFromJson(json : String, city : City)

    suspend fun getItineraryDirections(codeLine:String) :List<String>

    suspend fun getAllItineraries(codeLine: String) : List<BusStop>

    suspend fun getItinerary(codeLine: String, direction: String) : List<BusStop>

}