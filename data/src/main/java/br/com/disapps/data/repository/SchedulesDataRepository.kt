package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.SchedulesDataSourceFactory
import br.com.disapps.data.entity.mappers.toLineScheduleBO
import br.com.disapps.data.entity.mappers.toScheduleBO
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.model.Schedule
import br.com.disapps.domain.repository.SchedulesRepository
import io.reactivex.Completable
import io.reactivex.Single

class SchedulesDataRepository( private val schedulesDataSourceFactory: SchedulesDataSourceFactory) : SchedulesRepository {

    override fun jsonSchedules(): Single<String> {
        return  schedulesDataSourceFactory
                .create(true)
                .jsonSchedules()
    }

    override fun saveAllFromJson(json: String): Completable {
        return schedulesDataSourceFactory
                .create()
                .saveAllFromJson(json)
    }

    override fun getLineSchedulesDays(codeLine: String): Single<List<Int>> {
        return schedulesDataSourceFactory.create().getLineSchedulesDays(codeLine)
    }

    override fun getLineSchedules(codeLine: String, day: Int): Single<List<LineSchedule>> {
        return schedulesDataSourceFactory
                .create()
                .getLineSchedules(codeLine, day)
                .map{ s -> s.toLineScheduleBO()}
    }

    override fun getAllPointSchedules(codeLine: String, day: Int, codePoint: String): Single<List<Schedule>> {
        return schedulesDataSourceFactory
                .create()
                .getAllPointSchedules(codeLine,day, codePoint)
                .map { s -> s.toScheduleBO() }
    }
}