package com.manubla.restoya.presentation.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.restoya.data.repository.restaurants.RestaurantsSourceRepository
import com.manubla.restoya.data.service.response.RestaurantsPageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val restaurantsRepository: RestaurantsSourceRepository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val data: LiveData<RestaurantsPageResponse>
        get() = localData

    private val localData = MutableLiveData<RestaurantsPageResponse>()

    fun loadData(latitude: Double?, longitude: Double?, offset: Int) {
        launch(Dispatchers.IO) {
            localData.postValue(
                restaurantsRepository.getRestaurantsPage(latitude, longitude, offset))
        }
    }

}