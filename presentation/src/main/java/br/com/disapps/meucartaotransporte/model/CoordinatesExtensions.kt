package br.com.disapps.meucartaotransporte.model

import br.com.disapps.domain.model.Bus
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.model.Coordinate
import br.com.disapps.domain.model.Shape
import com.google.android.gms.maps.model.LatLng
import java.util.*

fun BusStop.getLatLng(): LatLng {
    val lat = this.latitude.replace(',', '.')
    val lng = this.longitude.replace(',', '.')
    return LatLng(lat.toDouble(), lng.toDouble())
}

fun Bus.getLatLng(): LatLng {
    val lat = this.latitude.replace(',', '.')
    val lng = this.longitude.replace(',', '.')
    return LatLng(lat.toDouble(), lng.toDouble())
}

fun Coordinate.getLatLng(): LatLng {
    val lat = this.latitude.replace(',', '.')
    val lng = this.longitude.replace(',', '.')
    return LatLng(lat.toDouble(), lng.toDouble())
}

fun Shape.getAllCoordinates(): ArrayList<LatLng> {
    val stops = ArrayList<LatLng>()
    for (coordinate in coordinates) {
        stops.add(coordinate.getLatLng())
    }
    return stops
}
