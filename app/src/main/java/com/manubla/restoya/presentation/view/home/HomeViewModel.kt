package com.manubla.restoya.presentation.view.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.restoya.data.repository.restaurants.RestaurantsSourceRepository
import com.manubla.restoya.data.service.LocationService
import com.manubla.restoya.data.service.response.RestaurantsPageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val restaurantsRepository: RestaurantsSourceRepository,
                    private val locationService: LocationService) : ViewModel(), CoroutineScope {

    private var mLatitude: Double? = null
    private var mLongitude: Double? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val data: LiveData<RestaurantsPageResponse>
        get() = localData

    private val localData = MutableLiveData<RestaurantsPageResponse>()

    fun loadData(offset: Int) {
        if (mLatitude != null && mLongitude != null)
            fetchRestaurants(offset)
        else {
            //Get location first and then fetch restaurants
            val locationResult = object : LocationService.LocationResult {
                override fun gotLocation(location: Location?) {
                    location?.let {
                        mLatitude = it.latitude
                        mLongitude = it .longitude
                    }
                    fetchRestaurants(offset)
                }
            }

            if (!locationService.getLocation(locationResult))
                fetchRestaurants(offset) //Network disabled or something went wrong
        }
    }


    fun fetchRestaurants(offset: Int) {
        launch(Dispatchers.IO) {
            localData.postValue(
                restaurantsRepository.getRestaurantsPage(mLatitude, mLongitude, offset)
            )
        }
    }

}