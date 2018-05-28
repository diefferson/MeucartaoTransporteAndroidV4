package br.com.disapps.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by diefferson.santos on 10/05/17.
 */
class Extrato {
    @SerializedName("saldo")
    var saldo: Double = 0.0
    @SerializedName("local")
    var local: String = ""
    @SerializedName("data")
    var data: String = ""
    @SerializedName("valor")
    var valor: Double = 0.0
}
