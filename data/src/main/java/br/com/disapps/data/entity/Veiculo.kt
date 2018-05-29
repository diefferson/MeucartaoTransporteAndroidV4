package br.com.disapps.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by diefferson.santos on 05/06/17.
 */
class Veiculo (
    @SerializedName("prefixo")
    var prefixo: String,
    @SerializedName("hora")
    var hora: String,
    @SerializedName("latitude")
    var latitude: String,
    @SerializedName("longitude")
    var longitude: String,
    @SerializedName("linha")
    var linha: String
)