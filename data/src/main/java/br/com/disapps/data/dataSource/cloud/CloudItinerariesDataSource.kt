package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.DownloadClient.getRetrofitDownloadClient
import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ItinerariesDataSource
import br.com.disapps.data.entity.Ponto
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City

class CloudItinerariesDataSource(private val restApi: RestApi) : ItinerariesDataSource {

    override suspend fun jsonItineraries(city: City, downloadProgressListener: DownloadProgressListener): String {
        return getRetrofitDownloadClient(downloadProgressListener).listaPontos(city.toString())
                .await()
                .toString()
    }

    override suspend fun saveAllFromJson(json: String, city: City) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun getItineraryDirections(codeLine: String): List<String> {
        throw Throwable("not implemented, only local")
    }

    override suspend fun getItinerary(codeLine: String, direction: String): List<Ponto> {
        throw Throwable("not implemented, only local")
    }

    override suspend fun getAllItineraries(codeLine: String): List<Ponto> {
        throw Throwable("not implemented, only local")
    }
}