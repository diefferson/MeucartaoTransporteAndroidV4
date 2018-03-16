package br.com.disapps.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by dnso on 16/03/2018.
 */
class RequestCartao{
    @SerializedName("c") var codigo : String = ""
    @SerializedName("d") var cpf: String = ""
    @SerializedName("t") var tipoConsulta: String = ""
}
