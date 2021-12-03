package com.manubla.freemarket.presentation.view.map

import android.content.Context
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.manubla.freemarket.R
import com.manubla.freemarket.data.model.Restaurant
import com.manubla.freemarket.presentation.helper.enableAndRefresh
import com.manubla.freemarket.presentation.util.showLongErrorMessage
import com.manubla.freemarket.presentation.util.showLongMessage
import com.manubla.freemarket.presentation.util.showLongSuccessMessage
import com.manubla.freemarket.presentation.view.home.ActivityCallback
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(), ActivityCallback, OnMapReadyCallback {

    private val mViewModel: MapViewModel by viewModel()
    private var mListener: OnFragmentInteractionListener? = null
    private var mMap: GoogleMap? = null
    private var mUserLat: Double? = null
    private var mUserLong: Double? = null
    private var mEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeLayout.isEnabled = false
        mViewModel.data.observe(viewLifecycleOwner, Observer(this::dataChanged))
        mViewModel.location.observe(viewLifecycleOwner, Observer(this::locationChanged))
        (childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment).getMapAsync(this)
    }

    override fun onRefresh() {
        mMap?.apply {
            clear()
            if (mUserLat != null && mUserLong != null) {
                mUserLat?.let { latitude ->
                    mUserLong?.let { longitude ->
                        loadMarker(true, latitude, longitude, "Tu")
                    }
                }
            }
            else {
                swipeLayout.enableAndRefresh(true)
                mViewModel.getLocation()
            }
            mViewModel.loadData()
        }
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        try {
            mMap?.apply {
                setMinZoomPreference(7.5f)
                setOnMapClickListener { point ->
                    if (mEditMode) {
                        clear()
                        mUserLat = point.latitude
                        mUserLong = point.longitude
                        loadMarker(true, point.latitude, point.longitude, "Tu")
                        mViewModel.loadData()
                    }
                }
                setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.maps_style))
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("MapFragment", "Can't find style", e)
        }
        btnChangeLocation.setOnClickListener {
            if (!mEditMode) {
                mEditMode = true
                btnChangeLocation.text = getString(R.string.txt_confirmar)
                showLongMessage(getString(R.string.txt_change_location_help), view, context)
            } else {
                mEditMode = false
                btnChangeLocation.text = getString(R.string.txt_change_location)
                showLongSuccessMessage(getString(R.string.txt_change_location_success), view, context)
                mListener?.onFragmentInteraction(mUserLat, mUserLong)
            }
        }
        mViewModel.getLocation()
        mViewModel.loadData()
    }

    private fun dataChanged(data: List<Restaurant>) {
        data.forEach { restaurant ->
            val coordinates: List<Double> = restaurant.coordinates
                .split(",")
                .map {
                    it.trim()
                    it.toDouble()
                }
            if (coordinates.size == 2)
                loadMarker(false, coordinates[0], coordinates[1], restaurant.name)
        }

    }

    private fun locationChanged(location: Location?) {
        location?.let {
            mUserLat = it.latitude
            mUserLong = it.longitude
            loadMarker(true, it.latitude, it.longitude, "Tu")
        }
        if (swipeLayout.isRefreshing) {
            if (location == null) showLongErrorMessage(getString(R.string.txt_network_connection), view, context)
            swipeLayout.enableAndRefresh(false)
        }
    }

    private fun loadMarker(userLocation: Boolean, latitude: Double, longitude: Double, name: String) {
        val location = LatLng(latitude, longitude)
        val marker = MarkerOptions().apply {
            position(location)
            title(name)
            if (!userLocation)
                icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_store))
        }
        mMap?.apply {
            addMarker(marker).showInfoWindow()
            if (mUserLat == null || userLocation) {
                moveCamera(CameraUpdateFactory.newLatLng(location))
                moveCamera(CameraUpdateFactory.zoomTo(16.0f))
            }
        }

    }

    override fun onChangeLocation(lat: Double?, long: Double?) {
        //Do nothing
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(lat: Double?, long: Double?)
    }
}
