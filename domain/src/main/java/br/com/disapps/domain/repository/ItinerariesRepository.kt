package br.com.disapps.domain.repository

import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single

interface ItinerariesRepository{

    fun jsonItineraries(city : City) : Single<String>

    fun saveAllFromJson(json : String, city : City): Completable

}