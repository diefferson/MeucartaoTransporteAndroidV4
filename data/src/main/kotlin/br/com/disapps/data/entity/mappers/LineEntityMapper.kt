package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Linha
import br.com.disapps.domain.model.Line

fun Line.toLineDTO() = Linha().apply {
    codigo = this@toLineDTO.code
    nome = this@toLineDTO.name
    categoria = this@toLineDTO.category
    cor = this@toLineDTO.color
    favorito = this@toLineDTO.favorite
}

fun Linha.toLineBO()= Line(
    code = this.codigo,
    name = this.nome,
    category = this.categoria,
    color = this.cor,
    favorite = this.favorito
)

fun List<Line>.toLineDTO() = this.map { l -> l.toLineDTO() }

fun List<Linha>.toLineBO()= this.map { l -> l.toLineBO() }