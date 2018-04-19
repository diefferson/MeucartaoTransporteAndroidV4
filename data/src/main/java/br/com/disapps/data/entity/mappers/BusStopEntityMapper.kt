package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Ponto
import br.com.disapps.domain.model.BusStop


fun BusStop.toBusStopDTO() = Ponto().apply {
    numPonto = this@toBusStopDTO.code
    nomePonto = this@toBusStopDTO.name
    codigoLinha = this@toBusStopDTO.lineCode
    latitude = this@toBusStopDTO.latitude
    longitude = this@toBusStopDTO.longitude
    sequencia = this@toBusStopDTO.sequence
    sentido = this@toBusStopDTO.direction
    tipo = this@toBusStopDTO.type
}

fun Ponto.toBusStopBO()= BusStop(
        code = this.numPonto,
        name = this.nomePonto,
        lineCode = this.codigoLinha,
        latitude = this.latitude,
        longitude = this.longitude,
        sequence = this.sequencia,
        direction = this.sentido,
        type = this.tipo

)

fun List<BusStop>.toBusStopDTO() = this.map { it.toBusStopDTO() }

fun List<Ponto>.toBusStopBO()= this.map { it.toBusStopBO() }

