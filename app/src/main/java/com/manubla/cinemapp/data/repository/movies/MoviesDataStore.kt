package com.manubla.cinemapp.data.repository.movies

import com.manubla.cinemapp.data.model.Movie
import com.manubla.cinemapp.data.service.response.MoviesPageResponse

interface MoviesDataStore {
    suspend fun getMoviesPage(page: Int): MoviesPageResponse
    suspend fun getMoviesPage(page: Int, ratingMin: Double, ratingMax: Double): MoviesPageResponse
    suspend fun getMovie(movieId: Int): Movie?

}