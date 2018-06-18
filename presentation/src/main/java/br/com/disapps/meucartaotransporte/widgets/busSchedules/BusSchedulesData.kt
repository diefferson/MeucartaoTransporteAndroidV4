package br.com.disapps.meucartaotransporte.widgets.busSchedules

import br.com.disapps.domain.model.Schedule

data class BusSchedulesData(
    var codeLine : String = "",
    var nameLine : String = "",
    var colorLine : String = "",
    var codeStop : String = "",
    var nameStop : String = "",
    var nextSchedules :List<Schedule> = ArrayList()
)