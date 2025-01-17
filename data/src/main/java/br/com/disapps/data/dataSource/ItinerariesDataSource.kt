package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Ponto
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City

interface ItinerariesDataSource : DataSource{

    suspend fun saveAllFromJson(city : City,filePath:String)

    suspend fun getItineraryDirections(codeLine:String) :List<String>

    suspend fun getItinerary(codeLine: String, direction: String) : List<Ponto>

    suspend fun getAllItineraries(codeLine: String) : List<Ponto>

}