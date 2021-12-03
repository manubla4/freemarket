package com.manubla.freemarket.presentation.view.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.freemarket.data.model.Restaurant
import com.manubla.freemarket.data.repository.restaurants.RestaurantsSourceRepository
import com.manubla.freemarket.data.service.LocationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MapViewModel(private val restaurantsRepository: RestaurantsSourceRepository,
                   private val locationService: LocationService): ViewModel(), CoroutineScope {


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    val data: LiveData<List<Restaurant>>
        get() = localData
    val location: LiveData<Location?>
        get() = localLocation

    private val localData = MutableLiveData<List<Restaurant>>()
    private val localLocation = MutableLiveData<Location?>()


    fun loadData() {
        launch(Dispatchers.IO) {
            val data = restaurantsRepository.getAllStoredRestaurants()
            localData.postValue(data)
        }
    }

    fun getLocation() {
        val locationResult = object : LocationService.LocationResult {
            override fun gotLocation(location: Location?) {
                localLocation.postValue(location)
            }
        }
        if (!locationService.getLocation(locationResult))
            localLocation.postValue(null) //Network disabled or something went wrong
    }


}