package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.ShapesDataSource
import io.reactivex.Completable
import io.reactivex.Single

class CloudShapesDataSource(private val restApi: RestApi) : ShapesDataSource {

    override fun jsonShapes(city: String): Single<String> {
        return restApi.listaShapes(city).map { it.toString() }
    }

    override fun saveAllFromJson(json: String): Completable {
        return Completable.error(Throwable("not implemented, only local"))
    }
}