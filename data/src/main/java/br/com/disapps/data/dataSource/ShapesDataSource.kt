package br.com.disapps.data.dataSource

import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single

interface ShapesDataSource : DataSource{

    fun jsonShapes(city: City) : Single<String>

    fun saveAllFromJson(json : String, city: City): Completable
}