package br.com.disapps.meucartaotransporte.model

import java.io.Serializable

data class CardVO(
        var code: String,
        var cpf: String,
        var name: String = "",
        var type: String = "",
        var status: String = "",
        var balance: Double = 0.toDouble(),
        var balanceDate: String = ""
): Serializable
