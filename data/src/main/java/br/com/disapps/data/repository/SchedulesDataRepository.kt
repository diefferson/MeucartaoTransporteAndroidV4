package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.SchedulesDataSourceFactory
import br.com.disapps.data.entity.mappers.toLineScheduleBO
import br.com.disapps.data.entity.mappers.toScheduleBO
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.model.Schedule
import br.com.disapps.domain.repository.SchedulesRepository
import io.reactivex.Completable
import io.reactivex.Single

class SchedulesDataRepository( private val schedulesDataSourceFactory: SchedulesDataSourceFactory) : SchedulesRepository {


    override suspend fun saveAllFromJson(filePath:String, downloadProgressListener: DownloadProgressListener) {
        return schedulesDataSourceFactory
                .create()
                .saveAllFromJson(filePath, downloadProgressListener)
    }

    override suspend fun getLineSchedulesDays(codeLine: String): List<Int> {
        return schedulesDataSourceFactory.create().getLineSchedulesDays(codeLine)
    }

    override suspend fun getLineSchedules(codeLine: String, day: Int): List<LineSchedule> {
        return schedulesDataSourceFactory
                .create()
                .getLineSchedules(codeLine, day)
                .map{ it.toLineScheduleBO()}
    }

    override suspend fun getAllPointSchedules(codeLine: String, day: Int, codePoint: String): List<Schedule> {
        return schedulesDataSourceFactory
                .create()
                .getAllPointSchedules(codeLine,day, codePoint)
                .map { it.toScheduleBO() }
    }
}