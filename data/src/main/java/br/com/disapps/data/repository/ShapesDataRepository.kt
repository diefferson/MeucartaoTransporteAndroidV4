package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.ShapesDataSourceFactory
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ShapesRepository
import io.reactivex.Completable
import io.reactivex.Single

class ShapesDataRepository( private val shapesDataSourceFactory: ShapesDataSourceFactory) : ShapesRepository {

    override fun jsonShapes(city: City): Single<String> {
        return shapesDataSourceFactory
                .create(true)
                .jsonShapes(city)
    }

    override fun saveAllFromJson(json: String, city: City): Completable {
        return shapesDataSourceFactory
                .create()
                .saveAllFromJson(json, city)
    }
}