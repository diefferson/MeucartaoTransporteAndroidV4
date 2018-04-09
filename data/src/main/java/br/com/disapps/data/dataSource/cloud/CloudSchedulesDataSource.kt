package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.SchedulesDataSource
import io.reactivex.Completable
import io.reactivex.Single

class CloudSchedulesDataSource(private val restApi: RestApi) : SchedulesDataSource{

    override fun jsonSchedules(): Single<String> {
        return restApi.listaHorarios().map { it.toString() }
    }

    override fun saveAllFromJson(json: String): Completable {
        return Completable.error(Throwable("not implemented, only local"))
    }
}