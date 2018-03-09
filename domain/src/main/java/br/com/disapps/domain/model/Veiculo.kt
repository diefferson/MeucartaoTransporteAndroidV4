package br.com.disapps.domain.model

/**
 * Created by diefferson.santos on 05/06/17.
 */
data class Veiculo (
    var prefixo: String,
    var hora: String,
    var latitude: String,
    var longitude: String,
    var linha: String

//    val latLng: LatLng
//        get() {
//            val lat = this.latitude!!.replace(',', '.')
//            val lng = this.longitude!!.replace(',', '.')
//            return LatLng(java.lang.Double.valueOf(lat), java.lang.Double.valueOf(lng))
//        }
)
