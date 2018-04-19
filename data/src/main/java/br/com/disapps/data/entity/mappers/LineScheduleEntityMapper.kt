package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Horario
import br.com.disapps.data.entity.HorarioLinha
import br.com.disapps.domain.model.LineSchedule
import io.realm.RealmList


fun LineSchedule.toLineScheduleDTO() = HorarioLinha().apply {
    codigoLinha = this@toLineScheduleDTO.lineCode
    dia  = this@toLineScheduleDTO.day
    ponto = this@toLineScheduleDTO.busStopName
    numPonto = this@toLineScheduleDTO.busStopCode
    horarios = RealmList<Horario>().apply {
        addAll(this@toLineScheduleDTO.schedules.toScheduleDTO())
    }
}

fun HorarioLinha.toLineScheduleBO() = LineSchedule(
        lineCode = codigoLinha,
        day = dia,
        busStopName = ponto,
        busStopCode = numPonto,
        schedules = horarios.toScheduleBO()
)

fun List<LineSchedule>.toLineScheduleDTO()= this.map { it.toLineScheduleDTO() }

fun List<HorarioLinha>.toLineScheduleBO() = this.map { it.toLineScheduleBO() }

