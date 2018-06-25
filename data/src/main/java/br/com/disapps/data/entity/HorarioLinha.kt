package br.com.disapps.data.entity

import com.google.firebase.database.GenericTypeIndicator
import io.realm.RealmList
import io.realm.RealmObject

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

open class HorarioLinhaF{
    var codigoLinha: String = ""
    var dia: Int = 0
    var ponto: String = ""
    var numPonto: String = ""
    var horarios: GenericTypeIndicator<List<Horario>>? = GenericTypeIndicator()
}
