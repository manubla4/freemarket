package com.manubla.restoya.presentation.view.splash

import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.manubla.restoya.R
import com.manubla.restoya.presentation.view.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel.location.observe(this, Observer(this::locationChanged))
        splashViewModel.fetchTokenAndLocation()
    }


    private fun locationChanged(location: Location?) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(HomeActivity.KeyLatitude, location?.latitude)
        intent.putExtra(HomeActivity.KeyLongitude, location?.longitude)
        startActivity(intent)
        finish()
    }
}