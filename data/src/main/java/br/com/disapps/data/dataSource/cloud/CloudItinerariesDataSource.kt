package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ItinerariesDataSource
import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single

class CloudItinerariesDataSource(private val restApi: RestApi) : ItinerariesDataSource {

    override fun jsonItineraries(city: City): Single<String> {
        return restApi.listaPontos(city.toString()).map { it.toString() }
    }

    override fun saveAllFromJson(json: String, city: City): Completable {
        return Completable.error(Throwable("not implemented, only local"))
    }
}