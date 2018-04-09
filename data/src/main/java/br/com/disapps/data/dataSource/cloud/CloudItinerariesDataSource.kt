package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ItinerariesDataSource
import io.reactivex.Completable
import io.reactivex.Single

class CloudItinerariesDataSource(private val restApi: RestApi) : ItinerariesDataSource {

    override fun jsonItineraries(city: String): Single<String> {
        return restApi.listaPontos(city).map { it.toString() }
    }

    override fun saveAllFromJson(json: String): Completable {
        return Completable.error(Throwable("not implemented, only local"))
    }
}