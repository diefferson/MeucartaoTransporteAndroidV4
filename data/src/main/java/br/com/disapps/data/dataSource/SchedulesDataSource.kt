package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Horario
import br.com.disapps.data.entity.HorarioLinha
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.Schedule
import io.reactivex.Completable
import io.reactivex.Single

interface SchedulesDataSource : DataSource{

    fun jsonSchedules(downloadProgressListener: DownloadProgressListener) : Single<String>

    fun saveAllFromJson(json : String): Completable

    fun getLineSchedulesDays(codeLine : String) : Single<List<Int>>

    fun getLineSchedules(codeLine:String, day: Int) : Single<List<HorarioLinha>>

    fun getAllPointSchedules(codeLine: String, day: Int, codePoint : String) : Single<List<Horario>>

}