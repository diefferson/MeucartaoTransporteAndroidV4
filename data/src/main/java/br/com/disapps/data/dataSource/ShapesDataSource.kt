package br.com.disapps.data.dataSource

import io.reactivex.Completable
import io.reactivex.Single

interface ShapesDataSource : DataSource{

    fun jsonShapes(city: String) : Single<String>

    fun saveAllFromJson(json : String): Completable
}