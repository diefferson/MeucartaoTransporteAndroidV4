package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 09/05/17.
 */

data class LineSchedule(
    var lineCode: String,
    var day: Int = 0,
    var busStopName: String,
    var busStopCode: String,
    var schedules: List<Schedule>,
    var nextSchedules: List<Schedule> = ArrayList()
)
