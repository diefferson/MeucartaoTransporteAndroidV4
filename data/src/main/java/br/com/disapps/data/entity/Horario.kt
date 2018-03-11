package br.com.disapps.data.entity

import io.realm.RealmObject

/**
 * Created by diefferson.santos on 09/05/17.
 */
class Horario : RealmObject() {

    var tabelaHoraria: String = ""
    var adapt: String = ""
    var hora: String = ""

}
