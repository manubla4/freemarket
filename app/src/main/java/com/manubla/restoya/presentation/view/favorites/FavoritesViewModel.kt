package com.manubla.restoya.presentation.view.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.restoya.data.model.Restaurant
import com.manubla.restoya.data.repository.restaurants.RestaurantsSourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel(private val restaurantsRepository: RestaurantsSourceRepository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val movies: LiveData<List<Restaurant>>
        get() = localMovies

    private val localMovies = MutableLiveData<List<Restaurant>>()

    fun loadMovies() {
        launch(Dispatchers.IO) {
            val movies = getFavoriteMovies()
            localMovies.postValue(movies)
        }
    }

    private suspend fun getFavoriteMovies(): List<Restaurant> = try {
        restaurantsRepository.getFavoriteMovies()
    } catch (error: Exception) {
        listOf()
    }


}