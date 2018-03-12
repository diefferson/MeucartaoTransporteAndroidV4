package br.com.disapps.data.entity

import io.realm.RealmObject

/**
 * Created by diefferson.santos on 09/05/17.
 */
open class Coordenada: RealmObject() {
    var latitude: String = ""
    var longitude: String = ""
}