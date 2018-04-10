package br.com.disapps.domain.repository

import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.model.Schedule
import io.reactivex.Completable
import io.reactivex.Single

interface SchedulesRepository{

    fun jsonSchedules() : Single<String>

    fun saveAllFromJson(json : String): Completable

    fun getLineSchedulesDays(codeLine : String) : Single<List<Int>>

    fun getLineSchedules(codeLine:String, day: Int) : Single<List<LineSchedule>>

    fun getAllPointSchedules(codeLine: String, day: Int, codePoint : String) : Single<List<Schedule>>
}