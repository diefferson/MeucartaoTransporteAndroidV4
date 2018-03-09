package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 09/05/17.
 */
data class Ponto(
    var numPonto: String,
    var nomePonto: String,
    var codigoLinha: String,
    var latitude: String,
    var longitude: String,
    var sequencia: Int = 0,
    var sentido: String,
    var tipo: String
)
//    val latLng: LatLng
//        get() {
//            val lat = this.latitude!!.replace(',', '.')
//            val lng = this.longitude!!.replace(',', '.')
//            return LatLng(java.lang.Double.valueOf(lat), java.lang.Double.valueOf(lng))
//        }