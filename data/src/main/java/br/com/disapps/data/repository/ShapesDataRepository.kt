package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.ShapesDataSourceFactory
import br.com.disapps.data.entity.mappers.toShapeBO
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.model.Shape
import br.com.disapps.domain.repository.ShapesRepository
import io.reactivex.Completable
import io.reactivex.Single

class ShapesDataRepository( private val shapesDataSourceFactory: ShapesDataSourceFactory) : ShapesRepository {

    override suspend fun jsonShapes(city: City, downloadProgressListener: DownloadProgressListener): String {
        return shapesDataSourceFactory
                .create(true)
                .jsonShapes(city, downloadProgressListener)
    }

    override suspend fun saveAllFromJson(json: String, city: City) {
        return shapesDataSourceFactory
                .create()
                .saveAllFromJson(json, city)
    }

    override suspend fun getShapes(codeLine: String): List<Shape> {
        return shapesDataSourceFactory
                .create()
                .getShapes(codeLine)
                .map { it.toShapeBO() }
    }

}