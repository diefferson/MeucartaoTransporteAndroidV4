package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 10/05/17.
 */
data class Card(
    var code: String,
    var cpf: String,
    var name: String = "",
    var type: String = "",
    var status: String = "",
    var balance: Double = 0.toDouble(),
    var balanceDate: String = "",
    var message: String = ""
)

