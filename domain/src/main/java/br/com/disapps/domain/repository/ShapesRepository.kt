package br.com.disapps.domain.repository

import io.reactivex.Completable
import io.reactivex.Single

interface ShapesRepository {

    fun jsonShapes(city : String) : Single<String>

    fun saveAllFromJson(json : String): Completable
}