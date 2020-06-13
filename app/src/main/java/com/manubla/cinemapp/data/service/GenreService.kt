package com.manubla.cinemapp.data.service

import com.manubla.cinemapp.data.service.response.GenreResponse
import retrofit2.http.GET

interface GenreService {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponse
}
