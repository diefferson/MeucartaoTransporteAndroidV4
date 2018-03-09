package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 10/05/17.
 */

data class RetornoExtrato (
    var code: String,
    var message: String,
    var content: List<Extrato>
)