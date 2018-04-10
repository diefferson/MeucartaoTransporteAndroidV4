package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Horario
import br.com.disapps.domain.model.Schedule

fun Schedule.toScheduleDTO() = Horario().apply {
     tabelaHoraria = this@toScheduleDTO.schedulesTable
     adapt  = if(this@toScheduleDTO.adapt) "ELEVADOR" else ""
     hora = this@toScheduleDTO.time
}

fun Horario.toScheduleBO() = Schedule(
        schedulesTable = this.tabelaHoraria,
        time = this.hora,
        adapt = this.adapt == "ELEVADOR"
)

fun List<Schedule>.toScheduleDTO()= this.map { s ->s.toScheduleDTO() }

fun List<Horario>.toScheduleBO() = this.map { s->s.toScheduleBO() }