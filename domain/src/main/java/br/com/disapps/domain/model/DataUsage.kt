package br.com.disapps.domain.model


data class DataUsage(
        var periodLines : String,
        var periodSchedules : String,
        var dateUpdateLines : Long,
        var dateUpdateSchedules : Long,
        var dateUpdateCwbItineraries : Long,
        var dateUpdateMetItineraries : Long,
        var dateUpdateCwbShapes : Long,
        var dateUpdateMetShapes : Long
)