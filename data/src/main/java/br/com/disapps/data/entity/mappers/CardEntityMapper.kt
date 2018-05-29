package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Cartao
import br.com.disapps.domain.model.Card

fun Cartao.toCardBO()= Card(
    code = this.codigo,
    cpf = this.cpf,
    name = this.nome,
    type = this.tipo,
    status = this.estado,
    balance = this.saldo,
    balanceDate = this.data_saldo
)

fun Card.toCardDTO() = Cartao().apply {
    codigo = this@toCardDTO.code
    cpf = this@toCardDTO.cpf
    nome = this@toCardDTO.name
    tipo = this@toCardDTO.type
    estado = this@toCardDTO.status
    saldo = this@toCardDTO.balance
    data_saldo = this@toCardDTO.balanceDate
}

fun List<Cartao>.toCardBO() = this.map{ it.toCardBO() }

fun List<Card>.toCardDTO() = this.map{ it.toCardDTO() }