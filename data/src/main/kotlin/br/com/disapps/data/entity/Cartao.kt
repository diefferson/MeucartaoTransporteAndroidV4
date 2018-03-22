package br.com.disapps.data.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/**
 * Created by diefferson.santos on 10/05/17.
 */
open class Cartao : RealmObject() {

    @PrimaryKey
    var codigo: String = ""
    var cpf: String = ""
    var nome: String = ""
    var tipo: String = ""
    var estado: String = ""
    var saldo: Double = 0.toDouble()

    @SerializedName("data_saldo")
    var dataSaldo: String = ""

    @Ignore
    var mensagem: String = ""

}