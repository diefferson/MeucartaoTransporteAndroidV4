package br.com.disapps.data.dataSource

import io.reactivex.Completable
import io.reactivex.Single

interface SchedulesDataSource : DataSource{

    fun jsonSchedules() : Single<String>

    fun saveAllFromJson(json : String): Completable

}