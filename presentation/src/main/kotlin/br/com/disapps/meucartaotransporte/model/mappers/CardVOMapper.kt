package br.com.disapps.meucartaotransporte.model.mappers

import br.com.disapps.domain.model.Card
import br.com.disapps.meucartaotransporte.model.CardVO

fun Card.toCardVO() = CardVO(
   code = this.code,
   cpf = this.cpf,
   name = this.name,
   type = this.type,
   status = this.status,
   balance = this.balance,
   balanceDate = this.balanceDate
)

fun CardVO.toCardBO()= Card(
    code = this.code,
    cpf = this.cpf,
    name = this.name,
    type = this.type,
    status = this.status,
    balance = this.balance,
    balanceDate = this.balanceDate
)

fun List<Card>.toCardVO() = this.map { c -> c.toCardVO() }

fun List<CardVO>.toCardBO()= this.map { c -> c.toCardBO() }