package br.com.disapps.data.dataSource

import io.reactivex.Completable
import io.reactivex.Single

interface ItinerariesDataSource : DataSource{

    fun saveAllFromJson(json : String): Completable

    fun jsonItineraries(city: String) : Single<String>

}