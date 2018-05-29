package br.com.disapps.data.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/**
 * Created by diefferson.santos on 10/05/17.
 */
open class Cartao : RealmObject() {

    @SerializedName("codigo")
    @PrimaryKey
    var codigo: String = ""
    @SerializedName("cpf")
    var cpf: String = ""
    @SerializedName("nome")
    var nome: String = ""
    @SerializedName("tipo")
    var tipo: String = ""
    @SerializedName("estado")
    var estado: String = ""
    @SerializedName("saldo")
    var saldo: Double = 0.toDouble()

    @SerializedName("data_saldo")
    var data_saldo: String = ""

    @Ignore
    @SerializedName("mensagem")
    var mensagem: String = ""

}