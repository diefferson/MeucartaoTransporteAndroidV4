package br.com.disapps.meucartaotransporte.ui.line.shapes

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.disapps.domain.model.Bus
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.getAllCoordinates
import br.com.disapps.meucartaotransporte.model.getLatLng
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.util.LatLngInterpolator
import br.com.disapps.meucartaotransporte.util.animateMarkerToGB
import br.com.disapps.meucartaotransporte.util.markerFactory
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_shapes.*
import org.koin.android.architecture.ext.viewModel


/**
 * Created by dnso on 12/03/2018.
 */
class ShapesFragment : BaseFragment(), OnMapReadyCallback{

    override val viewModel by viewModel<ShapesViewModel>()
    override val fragmentLayout = R.layout.fragment_shapes
    private val lineViewModel  by viewModel<LineViewModel>()

    private lateinit var googleMap: GoogleMap
    private val stopsMarkers = ArrayList<Marker>()
    private val busesMarkers = HashMap<String,Marker>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        viewModel.init(lineViewModel.line.code)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(p0: GoogleMap) {
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.activity!!)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }

        googleMap = p0
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_json))
        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.shapes.observe(this, Observer {shapes ->
            shapes?.forEach {shape ->
                val coordinates = shape.getAllCoordinates()
                if(coordinates.size >0){
                    googleMap.apply {
                        moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates[coordinates.size / 2], 13f))
                        addPolyline(PolylineOptions()
                                    .addAll(coordinates)
                                    .color(ContextCompat.getColor(context!!,R.color.polyline))
                                    .width(20f))
                    }
                }
            }
        })

        viewModel.stops.observe(this, Observer {stops->
            stops?.forEach {
                val markStop = markerFactory(googleMap,
                                it.getLatLng(),
                                it.name,
                                getString(R.string.direction, it.direction),
                                false,
                                R.drawable.bus_2)

                stopsMarkers.add(markStop)
            }
        })

        viewModel.buses.observe(this, Observer {buses ->
            clearBuses(buses)
            buses?.forEach {
                if(busesMarkers.size >0){
                    if(busesMarkers.containsKey(it.prefix)){
                        animateMarkerToGB(busesMarkers[it.prefix]!!, it.getLatLng(), LatLngInterpolator.Linear())
                    }else{
                       addBus(it)
                    }
                }else{
                    addBus(it)
                }
            }
        })
    }

    private fun addBus(bus : Bus){
        busesMarkers[bus.prefix] = markerFactory(googleMap,
                bus.getLatLng(),
                bus.prefix,
                getString(R.string.bus_updated, bus.time),
                true,
                R.drawable.bus_side )
    }

    private fun clearBuses(buses : List<Bus>?){

        val listDisabled = ArrayList<String>()
        busesMarkers.forEach { t, u ->
            var exists = false
            buses?.forEach {bus ->
                if(bus.prefix == t){
                    exists = true
                }
            }

            if(!exists){
                busesMarkers[t]?.isVisible = false
                listDisabled.add(t)
            }
        }

        listDisabled.forEach{
            if(busesMarkers.containsKey(it)){
                busesMarkers.remove(it)
            }
        }
    }

    companion object {
        fun newInstance() = ShapesFragment()
    }
}