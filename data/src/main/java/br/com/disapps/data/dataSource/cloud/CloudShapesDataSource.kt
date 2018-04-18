package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.DownloadClient.getRetrofitDownloadClient
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ShapesDataSource
import br.com.disapps.domain.model.City
import io.reactivex.Completable
import io.reactivex.Single



class CloudShapesDataSource(private val restApi: RestApi) : ShapesDataSource {

    override fun jsonShapes(city: City, downloadProgressListener: DownloadProgressListener): Single<String> {

        return getRetrofitDownloadClient(downloadProgressListener)
                .listaShapes(city.toString()).map { it.toString() }
    }

    override fun saveAllFromJson(json: String, city: City): Completable {
        return Completable.error(Throwable("not implemented, only local"))
    }
}