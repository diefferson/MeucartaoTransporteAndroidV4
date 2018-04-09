package br.com.disapps.domain.repository

import io.reactivex.Completable
import io.reactivex.Single

interface ItinerariesRepository{

    fun jsonItineraries(city : String) : Single<String>

    fun saveAllFromJson(json : String): Completable

}