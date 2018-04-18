package br.com.disapps.data.dataSource.cloud

import br.com.disapps.data.api.DownloadClient.getRetrofitDownloadClient
import br.com.disapps.data.api.RestApi
import br.com.disapps.data.dataSource.SchedulesDataSource
import br.com.disapps.data.entity.Horario
import br.com.disapps.data.entity.HorarioLinha
import br.com.disapps.domain.listeners.DownloadProgressListener
import io.reactivex.Completable
import io.reactivex.Single

class CloudSchedulesDataSource(private val restApi: RestApi) : SchedulesDataSource{

    override fun jsonSchedules(downloadProgressListener: DownloadProgressListener): Single<String> {
        return getRetrofitDownloadClient(downloadProgressListener)
                .listaHorarios().map { it.toString() }
    }

    override fun saveAllFromJson(json: String): Completable {
        return Completable.error(Throwable("not implemented, only local"))
    }

    override fun getLineSchedulesDays(codeLine: String): Single<List<Int>> {
        return Single.error(Throwable("not implemented, only local"))
    }

    override fun getLineSchedules(codeLine: String, day: Int): Single<List<HorarioLinha>> {
        return Single.error(Throwable("not implemented, only local"))
    }

    override fun getAllPointSchedules(codeLine: String, day: Int, codePoint: String): Single<List<Horario>> {
        return Single.error(Throwable("not implemented, only local"))
    }
}