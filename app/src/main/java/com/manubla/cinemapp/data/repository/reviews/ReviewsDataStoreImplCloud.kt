package com.manubla.cinemapp.data.repository.reviews

import com.manubla.cinemapp.data.service.ReviewService
import com.manubla.cinemapp.data.service.response.ReviewsPageResponse

class ReviewsDataStoreImplCloud(private var reviewService: ReviewService) : ReviewsDataStore {

    override suspend fun getReviewsPage(movieId: Int, page: Int): ReviewsPageResponse {
        return reviewService.getReviewsPage(movieId, page).apply { fromCloud = true }
    }

}