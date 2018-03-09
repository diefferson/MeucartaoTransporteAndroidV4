package br.com.disapps.data.model

/**
 * Created by diefferson.santos on 09/05/17.
 */

data class Shape (
    var numShape: String,
    var codigoLinha: String,
    var coordenadas: List<Coordenada>

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

)
