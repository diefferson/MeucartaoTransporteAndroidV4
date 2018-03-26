package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.RequestCartao
import br.com.disapps.domain.model.Card

fun Card.toRequestCardDTO()= RequestCartao().apply {
    codigo = this@toRequestCardDTO.code
    cpf = this@toRequestCardDTO.cpf
    tipoConsulta= "saldo"
}

fun Card.toRequestExtractDTO()= RequestCartao().apply {
    codigo = this@toRequestExtractDTO.code
    cpf = this@toRequestExtractDTO.cpf
    tipoConsulta= "extrato"
}

fun RequestCartao.toCardBO()= Card(
        code = this.codigo,
        cpf = this.cpf
)

fun List<Card>.toRequestCardDTO() = this.map{ c-> c.toRequestCardDTO() }

fun List<RequestCartao>.toCardBO() = this.map{ c-> c.toCardBO() }