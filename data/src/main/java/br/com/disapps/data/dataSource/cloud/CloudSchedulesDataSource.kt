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

    override suspend fun jsonSchedules(downloadProgressListener: DownloadProgressListener): String {
        return getRetrofitDownloadClient(downloadProgressListener)
                .listaHorarios()
                .await()
                .toString()
    }

    override suspend fun saveAllFromJson(json: String) {
        throw Throwable("not implemented, only local")
    }

    override suspend fun getLineSchedulesDays(codeLine: String): List<Int> {
        throw Throwable("not implemented, only local")
    }

    override suspend fun getLineSchedules(codeLine: String, day: Int): List<HorarioLinha> {
        throw Throwable("not implemented, only local")
    }

    override suspend fun getAllPointSchedules(codeLine: String, day: Int, codePoint: String): List<Horario> {
        throw Throwable("not implemented, only local")
    }

    override suspend fun initSchedules() {
        throw Throwable("not implemented, only local")
    }
}