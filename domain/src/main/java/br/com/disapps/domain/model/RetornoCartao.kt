package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 10/05/17.
 */

data class RetornoCartao(
    var code: String,
    var message: String,
    var content: Cartao
)
