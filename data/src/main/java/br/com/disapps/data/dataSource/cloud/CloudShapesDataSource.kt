package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.DownloadClient.getRetrofitDownloadClient
import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ShapesDataSource
import br.com.disapps.data.entity.Shape
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City

class CloudShapesDataSource(private val restApi: RestApi) : ShapesDataSource {
    override suspend fun saveAllFromJson(filePath: String, city: City, downloadProgressListener: DownloadProgressListener) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun getShapes(codeLine: String): List<Shape> {
        throw Throwable("not implemented, only local")
    }
}