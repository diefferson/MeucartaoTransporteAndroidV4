package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 10/05/17.
 */
data class Cartao(
    var codigo: String,
    var cpf: String,
    var nome: String = "",
    var tipo: String = "",
    var estado: String = "",
    var saldo: Double = 0.toDouble(),
    var dataSaldo: String = "",
    var mensagem: String = ""
)

