package br.com.disapps.meucartaotransporte.ui.directions

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.android.synthetic.main.fragment_directions.*
import org.koin.android.architecture.ext.viewModel
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.maps.model.LatLngBounds
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.LinearLayout




class DirectionsFragment : BaseFragment() , OnMapReadyCallback, PlaceSelectionListener {

    override val viewModel by viewModel<DirectionsViewModel>()
    override val fragmentLayout = R.layout.fragment_directions
    override val fragmentTag= "DirectionsFragment"

    private lateinit var googleMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iAppActivityListener.setTitle(getString(R.string.app_name))
        iAppActivityListener.hideToolbar()
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val startPlaceAutocompleteFragment:PlaceAutocompleteFragment = start_place as PlaceAutocompleteFragment
        startPlaceAutocompleteFragment?.apply {
            setOnPlaceSelectedListener(this@DirectionsFragment)
            setBoundsBias(LatLngBounds(LatLng(-25.652708, -49.440451), LatLng(-25.320177, -49.114798)))
            this.view.findViewById<TextView>(R.id.place_autocomplete_search_input).hint = getString(R.string.address_start)
            val searchIcon = (this.view as LinearLayout).getChildAt(0) as ImageView
            searchIcon.setImageResource(R.drawable.map_marker)
        }

        val finisPlaceAutocompleteFragment = (fragmentManager!!.findFragmentById(R.id.finish_place) as? PlaceAutocompleteFragment)
        finisPlaceAutocompleteFragment?.apply {
            setOnPlaceSelectedListener(this@DirectionsFragment)
            setBoundsBias(LatLngBounds(LatLng(-25.652708, -49.440451), LatLng(-25.320177, -49.114798)))
            this.view.findViewById<TextView>(R.id.place_autocomplete_search_input).hint = getString(R.string.address_finish)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
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

    @SuppressLint("MissingPermission")
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
        val curitiba = LatLng(-25.444932, -49.275108)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curitiba, 10f))
    }

    private fun observeViewModel(){

    }

    override fun onPlaceSelected(p0: Place?) {
        Log.i("TESTE", "Place Selected: " + p0?.name)
    }

    override fun onError(p0: Status?) {
        Log.e("TESTE", "onError: Status = " + p0.toString())
    }

    companion object {
        fun newInstance() = DirectionsFragment()
    }
}