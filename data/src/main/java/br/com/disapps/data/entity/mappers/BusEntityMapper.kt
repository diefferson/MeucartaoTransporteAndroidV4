package br.com.disapps.data.entity.mappers

import br.com.disapps.data.entity.Veiculo
import br.com.disapps.domain.model.Bus

fun Bus.toBusDTO() = Veiculo(
        prefixo = this.prefix,
        hora = this.time,
        latitude = this.latitude,
        longitude = this.longitude,
        linha = this.line
)

fun Veiculo.toBusBO()= Bus(
        prefix = this.prefixo,
        time = this.hora,
        latitude = this.latitude,
        longitude = this.longitude,
        line = this.linha
)

fun List<Bus>.toBusDTO() = this.map { it.toBusDTO() }

fun List<Veiculo>.toBusBO()= this.map { it.toBusBO() }

