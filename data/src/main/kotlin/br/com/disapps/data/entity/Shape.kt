package br.com.disapps.data.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by diefferson.santos on 09/05/17.
 */

open class Shape: RealmObject() {

    @PrimaryKey
    var numShape: String = ""
    var codigoLinha: String = ""
    var coordenadas: RealmList<Coordenada> = RealmList()

}