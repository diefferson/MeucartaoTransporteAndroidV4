package br.com.disapps.data.dataSource

import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single

interface ItinerariesDataSource : DataSource{

    fun saveAllFromJson(json : String, city: City): Completable

    fun jsonItineraries(city: City) : Single<String>

}