package com.manubla.cinemapp.data.repository.reviews

import com.manubla.cinemapp.data.service.response.ReviewsPageResponse

interface ReviewsDataStore {
    suspend fun getReviewsPage(movieId: Int, page: Int): ReviewsPageResponse
}