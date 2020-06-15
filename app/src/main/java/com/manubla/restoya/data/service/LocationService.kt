package com.manubla.restoya.data.service

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import java.util.*

class LocationService (private val mLocationManager: LocationManager) {

    private var mLocationLoaded = false

    fun getLocation(result: LocationResult): Boolean {

        mLocationResult = result

        val statusOfGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val statusOfNetwork = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!statusOfGPS || !statusOfNetwork) return false

        try {
            if (statusOfGPS) {
                mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    mLocationListener
                )
            }
            else {
                mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0,
                    0f,
                    mLocationListener
                )
            }

            mTimeoutTimer.schedule(object : TimerTask() {
                override fun run() {
                    mLocationManager.removeUpdates(mLocationListener)
                    mLocationResult?.gotLocation(null)
                }
            }, 25000)

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
            try {
                mTimeoutTimer.cancel()
                mLocationManager.removeUpdates(this)
                mLocationResult?.gotLocation(location)
            }
            catch (e: Exception) {
                //ignore}
            }
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }

}
