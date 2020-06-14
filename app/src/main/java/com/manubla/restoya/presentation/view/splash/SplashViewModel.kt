package com.manubla.restoya.presentation.view.splash

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.restoya.data.repository.token.TokenDataStoreCloud
import com.manubla.restoya.data.repository.token.TokenDataStoreStorage
import com.manubla.restoya.data.service.LocationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SplashViewModel(private val tokenDataStoreCloud: TokenDataStoreCloud,
                      private val tokenDataStoreStorage: TokenDataStoreStorage,
                      private val locationService: LocationService) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val location: LiveData<Location?>
        get() = localLocation

    private val localLocation = MutableLiveData<Location?>()

    fun fetchTokenAndLocation() {
        launch(Dispatchers.IO) {
            val tokenResponse = tokenDataStoreCloud.getToken()
            tokenDataStoreStorage.storeToken(tokenResponse.access_token)

            val locationResult = object : LocationService.LocationResult {

                override fun gotLocation(location: Location?) {
                    localLocation.postValue(location)
                }
            }

            if (!locationService.getLocation(locationResult)) {

            }


        }
    }



}