package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Coordenada
import io.realm.RealmList
import br.com.disapps.data.entity.Shape as ShapeDTO
import br.com.disapps.domain.model.Shape as ShapeBO

fun ShapeBO.toShapeDTO() = ShapeDTO().apply {
    numShape = this@toShapeDTO.code
    codigoLinha  = this@toShapeDTO.lineCode
    coordenadas = RealmList<Coordenada>().apply {
        addAll(this@toShapeDTO.coordinates.toCoordinateDTO())
    }
}

fun ShapeDTO.toShapeBO() = ShapeBO(
        code = this.numShape,
        lineCode = this.codigoLinha,
        coordinates = this.coordenadas.toCoordinateBO()
)

fun List<ShapeBO>.toShapeDTO()= this.map { it.toShapeDTO() }

fun List<ShapeDTO>.toShapeBO() = this.map { it.toShapeBO() }