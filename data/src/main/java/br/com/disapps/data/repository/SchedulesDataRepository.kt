package br.com.disapps.data.repository

import br.com.disapps.data.dataSource.factory.SchedulesDataSourceFactory
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
}