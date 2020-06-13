package com.manubla.cinemapp.data.service

import com.manubla.cinemapp.data.service.response.MoviesPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getMoviesPage(@Query("language") language: String,
                              @Query("sort_by") sortBy: String,
                              @Query("include_adult") includeAdult: Boolean,
                              @Query("include_video") includeVideo: Boolean,
                              @Query("page") page: Int): MoviesPageResponse

    @GET("discover/movie")
    suspend fun getMoviesPage(@Query("language") language: String,
                              @Query("sort_by") sortBy: String,
                              @Query("include_adult") includeAdult: Boolean,
                              @Query("include_video") includeVideo: Boolean,
                              @Query("page") page: Int,
                              @Query("vote_average.gte") ratingMin: Double,
                              @Query("vote_average.lte") ratingMax: Double): MoviesPageResponse

    @GET("search/movie")
    suspend fun searchMoviesPage(@Query("language") language: String,
                              @Query("query") query: String,
                              @Query("page") page: Int,
                              @Query("include_adult") includeAdult: Boolean): MoviesPageResponse
}
