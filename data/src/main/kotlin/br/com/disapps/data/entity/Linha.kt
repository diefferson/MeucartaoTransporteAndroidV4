package br.com.disapps.data.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by diefferson.santos on 09/05/17.
 */
open class Linha: RealmObject() {

    @PrimaryKey
    var codigo: String = ""
    var nome: String = ""
    var categoria: String = ""
    var cor: String = ""
    var favorito: Int = 0

}
