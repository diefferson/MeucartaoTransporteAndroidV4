package br.com.disapps.meucartaotransporte.model

import java.io.Serializable

class SchedulesDetail(
        var lineCode: String,
        var day : Int,
        var busStopName: String,
        var busStopCode: String
): Serializable