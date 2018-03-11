package br.com.disapps.data.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by diefferson.santos on 09/05/17.
 */

class Shape: RealmObject() {

    @PrimaryKey
    var numShape: String = ""
    var codigoLinha: String = ""
    var coordenadas: List<Coordenada>? = null

}


//    val allCoords: ArrayList<LatLng>
//        get() {
//
//            val pontos = ArrayList<LatLng>()
//
//            for (coor in coordenadas!!) {
//                pontos.add(coor.latLng)
//            }
//
//            return pontos
//        }