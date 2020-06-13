package com.manubla.cinemapp.data.repository.reviews

import com.manubla.cinemapp.data.model.Review
import com.manubla.cinemapp.data.service.response.ReviewsPageResponse

interface ReviewsSourceRepository {
    suspend fun getReviewsPage(movieId: Int, page: Int): ReviewsPageResponse
    suspend fun storeReviews(reviews: List<Review>)
}