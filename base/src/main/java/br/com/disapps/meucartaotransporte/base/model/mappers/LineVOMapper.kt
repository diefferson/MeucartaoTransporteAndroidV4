package br.com.disapps.meucartaotransporte.base.model.mappers

import br.com.disapps.domain.model.Line
import br.com.disapps.meucartaotransporte.base.model.LineVO
import br.com.disapps.meucartaotransporte.base.util.clean

fun Line.toLineVO() = LineVO(
        code = this.code,
        name = this.name,
        category = this.category,
        color = this.color,
        favorite = this.favorite,
        searchableName = this.name.clean().toLowerCase()
)

fun LineVO.toLineBO() = Line(
        code = this.code,
        name = this.name,
        category = this.category,
        color = this.color,
        favorite = this.favorite
)

fun List<Line>.toLineVO() = this.map { it.toLineVO() }

fun List<LineVO>.toLineBO() = this.map { it.toLineBO() }