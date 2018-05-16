package br.com.disapps.data.dataSource

import br.com.disapps.data.entity.Horario
import br.com.disapps.data.entity.HorarioLinha
import br.com.disapps.domain.listeners.DownloadProgressListener

interface SchedulesDataSource : DataSource{

    suspend fun saveAllFromJson(filePath:String, downloadProgressListener: DownloadProgressListener)

    suspend fun getLineSchedulesDays(codeLine : String) : List<Int>

    suspend fun getLineSchedules(codeLine:String, day: Int) :List<HorarioLinha>

    suspend fun getAllPointSchedules(codeLine: String, day: Int, codePoint : String) : List<Horario>

    suspend fun initSchedules()
}