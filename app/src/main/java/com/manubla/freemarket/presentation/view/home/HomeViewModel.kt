package com.manubla.freemarket.presentation.view.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.freemarket.data.helper.networking.NetworkingManager
import com.manubla.freemarket.data.repository.restaurants.RestaurantsSourceRepository
import com.manubla.freemarket.data.service.LocationService
import com.manubla.freemarket.data.service.response.RestaurantsPageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val restaurantsRepository: RestaurantsSourceRepository,
                    private val locationService: LocationService,
                    private val networkingManager: NetworkingManager) : ViewModel(), CoroutineScope {

    private var mLatitude: Double? = null
    private var mLongitude: Double? = null
    private var mOffset: Int = 0

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    val data: LiveData<RestaurantsPageResponse>
        get() = localData
    val networkOnline: LiveData<Boolean>
        get() = localNetworkOnline

    private val localData = MutableLiveData<RestaurantsPageResponse>()
    private val localNetworkOnline = MutableLiveData<Boolean>()


    fun isNetworkOnline() {
        localNetworkOnline.postValue(networkingManager.isNetworkOnline())
    }

    fun loadData(offset: Int, lat: Double?, long: Double?) {
        mOffset = offset
        if (lat == null || long == null)
            loadData(mOffset)
        else {
            mLatitude = lat
            mLongitude = long
            fetchRestaurants(mOffset)
        }
    }

    fun loadData(offset: Int) {
        mOffset = offset
        if (mLatitude != null && mLongitude != null)
            fetchRestaurants(mOffset)
        else {
            //Get location first and then fetch restaurants
            val locationResult = object : LocationService.LocationResult {
                override fun gotLocation(location: Location?) {
                    location?.let {
                        mLatitude = it.latitude
                        mLongitude = it .longitude
                    }
                    fetchRestaurants(mOffset)
                }
            }

            if (!locationService.getLocation(locationResult))
                fetchRestaurants(mOffset) //Network disabled or something went wrong
        }
    }


    private fun fetchRestaurants(offset: Int) {
        launch(Dispatchers.IO) {
            val data = restaurantsRepository.getRestaurantsPage(mLatitude, mLongitude, offset)
            if (mOffset == offset)  //FIXME fix this properly, weird coroutines bug
                localData.postValue(data)
        }
    }

}