package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.ShapesDataSourceFactory
import br.com.disapps.data.entity.mappers.toShapeBO
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.model.Shape
import br.com.disapps.domain.repository.ShapesRepository

class ShapesDataRepository( private val shapesDataSourceFactory: ShapesDataSourceFactory) : ShapesRepository {

    override suspend fun saveAllFromJson(city : City,filePath: String) {
        return shapesDataSourceFactory
                .create()
                .saveAllFromJson( city, filePath)
    }

    override suspend fun getShapes(codeLine: String): List<Shape> {
        return shapesDataSourceFactory
                .create()
                .getShapes(codeLine)
                .map { it.toShapeBO() }
    }

}