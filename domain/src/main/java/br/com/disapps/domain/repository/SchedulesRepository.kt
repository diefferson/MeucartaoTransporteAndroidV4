package br.com.disapps.domain.repository

import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.model.Schedule

interface SchedulesRepository{

    suspend fun jsonSchedules(downloadProgressListener: DownloadProgressListener) : String

    suspend fun saveAllFromJson(json : String)

    suspend fun getLineSchedulesDays(codeLine : String) : List<Int>

    suspend fun getLineSchedules(codeLine:String, day: Int) : List<LineSchedule>

    suspend fun getAllPointSchedules(codeLine: String, day: Int, codePoint : String) : List<Schedule>

    suspend fun initSchedules()
}