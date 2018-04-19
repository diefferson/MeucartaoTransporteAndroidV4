package br.com.disapps.meucartaotransporte.util

import android.os.Handler
import android.os.SystemClock
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

fun markerFactory(mMap: GoogleMap, position : LatLng, title: String, snippet: String, visible :Boolean, icon: Int): Marker {
    val markerOptions = MarkerOptions()
            .position(position)
            .title(title)
            .snippet(snippet)
            .visible(visible)
            .icon(BitmapDescriptorFactory.fromResource(icon))
    return mMap.addMarker(markerOptions)
}

fun animateMarkerToGB(marker: Marker, finalPosition: LatLng, latLngInterpolator: LatLngInterpolator) {
    val startPosition = marker.position
    val handler = Handler()
    val start = SystemClock.uptimeMillis()
    val interpolator = AccelerateDecelerateInterpolator()
    val durationInMs = 3000f

    handler.post(object : Runnable {

        internal var elapsed: Long = 0
        internal var t: Float = 0.toFloat()
        internal var v: Float = 0.toFloat()

        override fun run() {
            // Calculate progress using interpolator
            elapsed = SystemClock.uptimeMillis() - start
            t = elapsed / durationInMs
            v = interpolator.getInterpolation(t)

            marker.position = latLngInterpolator.interpolate(v, startPosition, finalPosition)

            // Repeat till progress is complete.
            if (t < 1) {
                // Post again 16ms later.
                handler.postDelayed(this, 16)
            }
        }
    })
}