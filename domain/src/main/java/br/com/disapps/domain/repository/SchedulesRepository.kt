package br.com.disapps.domain.repository

import io.reactivex.Completable
import io.reactivex.Single

interface SchedulesRepository{

    fun jsonSchedules() : Single<String>

    fun saveAllFromJson(json : String): Completable
}