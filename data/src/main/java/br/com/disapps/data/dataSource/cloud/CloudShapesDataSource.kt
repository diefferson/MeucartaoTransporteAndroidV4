package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.DownloadClient.getRetrofitDownloadClient
import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ShapesDataSource
import br.com.disapps.data.entity.Shape
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City

class CloudShapesDataSource(private val restApi: RestApi) : ShapesDataSource {

    override suspend fun jsonShapes(city: City, downloadProgressListener: DownloadProgressListener): String {
        return getRetrofitDownloadClient(downloadProgressListener)
                .listaShapes(city.toString())
                .await()
                .toString()
    }

    override suspend fun saveAllFromJson(json: String, city: City) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun getShapes(codeLine: String): List<Shape> {
        throw Throwable("not implemented, only local")
    }
}