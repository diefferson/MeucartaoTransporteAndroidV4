package br.com.disapps.meucartaotransporte.ui.line.shapes

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.disapps.domain.model.Bus
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.getAllCoordinates
import br.com.disapps.meucartaotransporte.model.getLatLng
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.util.*
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.fragment_shapes.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.withContext
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
        show_stops.setOnClickListener{ showStops() }
        show_help.setOnClickListener{
            help_text.visibility = View.VISIBLE
            async {
                delay(10000)
                withContext(UI){
                    help_text.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        viewModel.getIsDownloaded(getCity(lineViewModel.line.category))
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

        viewModel.isDownloaded.observe(this, Observer {
            it?.let {
                if(it){
                    viewModel.init(lineViewModel.line.code)
                }else{
                    error_view?.addView(activity?.getDownloadDataView())
                    error_view.visibility = View.VISIBLE
                    iAppActivityListener.hideTabs()
                }
            }
        })

        viewModel.shapes.observe(this, Observer {shapes ->
            if(shapes != null && shapes.isNotEmpty()){
                shapes.forEach {shape ->
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
            }else{
                error_view?.addView(activity?.getEmptyView(getString(R.string.no_shape_data)))
                error_view.visibility = View.VISIBLE
                iAppActivityListener.hideTabs()
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
                        updateBus(it)
                    }else{
                       addBus(it)
                    }
                }else{
                    addBus(it)
                }
            }
        })
    }

    private fun updateBus(bus : Bus){
        busesMarkers[bus.prefix]?.snippet = getString(R.string.bus_updated, bus.time)
        animateMarkerToGB(busesMarkers[bus.prefix]!!, bus.getLatLng(), LatLngInterpolator.Linear())
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
        if(busesMarkers.size >0){
            busesMarkers.forEach { t, _ ->
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
        }

        listDisabled.forEach{
            if(busesMarkers.containsKey(it)){
                busesMarkers.remove(it)
            }
        }
    }

    private fun showStops(){
        if(!viewModel.showStops){
            viewModel.showStops = true
            stopsMarkers.forEach {
                it.isVisible =true
            }
        }else{
            viewModel.showStops = false
            stopsMarkers.forEach {
                it.isVisible = false
            }
        }
    }

    companion object {
        fun newInstance() = ShapesFragment()
    }
}