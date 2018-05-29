package br.com.disapps.data.entity

import com.google.gson.annotations.SerializedName

open class BaseResponse<T> {
    @SerializedName("code")
    var code: String = ""
    @SerializedName("message")
    var message: String = ""
    @SerializedName("content")
    var content: T? = null
}

class RetornoCartao : BaseResponse<Cartao>()

class RetornoExtrato : BaseResponse<List<Extrato>>()