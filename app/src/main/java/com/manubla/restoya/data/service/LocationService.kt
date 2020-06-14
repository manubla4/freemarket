package com.manubla.restoya.data.service

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import java.util.*

class LocationService (private var mLocationManager: LocationManager) {

    fun getLocation(context: Context, result: LocationResult): Boolean {

        mLocationResult = result

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val networkEnabled = mLocationManager.isLocationEnabled
            if (!networkEnabled)
                return false
        } else {
            val providers = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED
            )
            val networkEnabled =
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                                || providers.contains("network")
            if (!networkEnabled)
                return false
        }

        try {
            mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0f,
                mLocationListener
            )
            mTimeoutTimer.schedule(object : TimerTask() {
                override fun run() {
                    mLocationManager.removeUpdates(mLocationListener)
                    mLocationResult?.gotLocation(null)
                }
            }, 30000)

            return true

        } catch (e: SecurityException) {
            Log.e("LocationUtils", e.stackTrace.toString())
        }

        return false
    }


    interface LocationResult {
        fun gotLocation(location: Location?)
    }

    private var mTimeoutTimer = Timer()
    private var mLocationResult: LocationResult? = null
    private val mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            mTimeoutTimer.cancel()
            mLocationManager.removeUpdates(this)
            mLocationResult?.gotLocation(location)
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }

}
