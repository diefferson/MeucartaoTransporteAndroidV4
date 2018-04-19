package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Coordenada
import br.com.disapps.domain.model.Coordinate

fun Coordinate.toCoordinateDTO() = Coordenada().apply {
    latitude = this@toCoordinateDTO.latitude
    longitude  = this@toCoordinateDTO.longitude
}

fun Coordenada.toCoordinateBO() = Coordinate(
        latitude = this.latitude,
        longitude = this.longitude
)

fun List<Coordinate>.toCoordinateDTO()= this.map { it.toCoordinateDTO() }

fun List<Coordenada>.toCoordinateBO() = this.map { it.toCoordinateBO() }