package br.com.disapps.data.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore

/**
 * Created by diefferson.santos on 09/05/17.
 */

open class HorarioLinha :  RealmObject() {
    var codigoLinha: String = ""
    var dia: Int = 0
    var ponto: String = ""
    var numPonto: String = ""
    var horarios: RealmList<Horario> = RealmList()
}
