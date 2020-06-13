package com.manubla.cinemapp.presentation.view.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.repository.movies.MoviesSourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoritesViewModel(private val moviesRepository: MoviesSourceRepository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val movies: LiveData<List<Movie>>
        get() = localMovies

    private val localMovies = MutableLiveData<List<Movie>>()

    fun loadMovies() {
        launch(Dispatchers.IO) {
            val movies = getFavoriteMovies()
            localMovies.postValue(movies)
        }
    }

    private suspend fun getFavoriteMovies(): List<Movie> = try {
        moviesRepository.getFavoriteMovies()
    } catch (error: Exception) {
        listOf()
    }


}