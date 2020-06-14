package com.manubla.restoya.presentation.view.splash

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.manubla.restoya.R
import com.manubla.restoya.presentation.view.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {
    private val mViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
        else fetchData()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                checkLocationEnabled()
            else {
                Toast.makeText(this, getString(R.string.txt_necessary_permission), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun checkLocationEnabled() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            expirationTime = 20000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        LocationServices.getSettingsClient(this).checkLocationSettings(builder.build()).apply {

            addOnCompleteListener { task ->
                try {
                    task.getResult(ApiException::class.java)
                    fetchData()  //Location is correctly enabled

                } catch (exception: ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            val resolvable = exception as ResolvableApiException
                            startIntentSenderForResult(
                                resolvable.resolution.intentSender,
                                REQUEST_LOCATION_SETTINGS,
                                null,
                                0,
                                0,
                                0,
                                null
                            )
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@SplashActivity,
                                getString(R.string.txt_location_enable),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            // Location setting is not available, should not happen
                            Toast.makeText(
                                this@SplashActivity,
                                getString(R.string.txt_location_unavailable),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_LOCATION_SETTINGS) {
            when (resultCode) {
                Activity.RESULT_OK ->
                    //User enabled token
                    fetchData()
                Activity.RESULT_CANCELED -> {
                    //User cancelled token activation
                    Toast.makeText(this, getString(R.string.txt_location_enable), Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }


    private fun fetchData() {
        mViewModel.token.observe(this, Observer(this::tokenChanged))
        mViewModel.fetchToken()
    }


    private fun tokenChanged(token: String) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val REQUEST_LOCATION_SETTINGS   = 2
    }
}