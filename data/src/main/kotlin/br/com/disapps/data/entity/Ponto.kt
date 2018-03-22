package br.com.disapps.data.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by diefferson.santos on 09/05/17.
 */
open class Ponto : RealmObject() {

    @PrimaryKey
    var numPonto: String = ""
    var nomePonto: String = ""
    var codigoLinha: String = ""
    var latitude: String = ""
    var longitude: String = ""
    var sequencia: Int = 0
    var sentido: String = ""
    var tipo: String = ""
}
