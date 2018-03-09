package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 09/05/17.
 */
data class Linha(
    var codigo: String,
    var nome: String,
    var categoria: String,
    var cor: String,
    var favorito: Int = 0
)
