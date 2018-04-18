package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.DownloadClient.getRetrofitDownloadClient
import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ItinerariesDataSource
import br.com.disapps.data.entity.Ponto
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single

class CloudItinerariesDataSource(private val restApi: RestApi) : ItinerariesDataSource {

    override fun jsonItineraries(city: City, downloadProgressListener: DownloadProgressListener): Single<String> {
        return getRetrofitDownloadClient(downloadProgressListener)
                .listaPontos(city.toString()).map { it.toString() }
    }

    override fun saveAllFromJson(json: String, city: City): Completable {
        return Completable.error(Throwable("not implemented, only local"))
    }

    override fun getItineraryDirections(codeLine: String): Single<List<String>> {
        return Single.error(Throwable("not implemented, only local"))
    }

    override fun getItinerary(codeLine: String, direction: String): Single<List<Ponto>> {
        return Single.error(Throwable("not implemented, only local"))
    }
}