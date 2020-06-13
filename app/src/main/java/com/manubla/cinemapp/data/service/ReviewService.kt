package com.manubla.cinemapp.data.service

import com.manubla.cinemapp.data.service.response.ReviewsPageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {

    @GET("movie/{movieId}/reviews")
    suspend fun getReviewsPage(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int): ReviewsPageResponse
}
