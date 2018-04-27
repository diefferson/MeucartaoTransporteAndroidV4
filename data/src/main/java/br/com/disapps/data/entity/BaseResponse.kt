package br.com.disapps.data.entity

open class BaseResponse<T> {
    var code: String = ""
    var message: String = ""
    var content: T? = null
}

class RetornoCartao : BaseResponse<Cartao>()

class RetornoExtrato : BaseResponse<List<Extrato>>()