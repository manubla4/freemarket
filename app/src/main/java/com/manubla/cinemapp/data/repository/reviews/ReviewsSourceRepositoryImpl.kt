package com.manubla.cinemapp.data.repository.reviews

import com.manubla.cinemapp.data.model.Review
import com.manubla.cinemapp.data.service.response.ReviewsPageResponse

class ReviewsSourceRepositoryImpl(var factory: ReviewsDataStoreFactory) :
    ReviewsSourceRepository {

    override suspend fun getReviewsPage(movieId: Int, page: Int): ReviewsPageResponse {
        return factory.reviewsDataStoreFactory.getReviewsPage(movieId, page)
    }

    override suspend fun storeReviews(reviews: List<Review>) {
        factory.reviewsDataStoreDatabase.storeReviews(reviews)
    }

}