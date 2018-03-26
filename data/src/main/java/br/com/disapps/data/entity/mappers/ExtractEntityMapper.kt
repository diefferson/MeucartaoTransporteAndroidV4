package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Extrato
import br.com.disapps.domain.model.Extract

fun Extrato.toExtractBO()= Extract(
        balance = this.saldo,
        date = this.data,
        value = this.valor,
        local = this.local
)

fun Extract.toExtractDTO() = Extrato().apply {
    saldo = this@toExtractDTO.balance
    data = this@toExtractDTO.date
    valor = this@toExtractDTO.value
    local = this@toExtractDTO.local
}

fun List<Extrato>.toExtractBO() = this.map{ e-> e.toExtractBO() }

fun List<Extract>.toExtractDTO() = this.map{ e -> e.toExtractDTO() }