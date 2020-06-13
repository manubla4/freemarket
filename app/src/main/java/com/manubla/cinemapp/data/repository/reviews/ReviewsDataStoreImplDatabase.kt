package com.manubla.cinemapp.data.repository.reviews

import com.manubla.cinemapp.data.dao.ReviewDao
import com.manubla.cinemapp.data.model.Review
import com.manubla.cinemapp.data.service.response.ReviewsPageResponse

class ReviewsDataStoreImplDatabase(private val reviewDao: ReviewDao) : ReviewsDataStore {
    private val pageRows = 10

    override suspend fun getReviewsPage(movieId: Int, page: Int): ReviewsPageResponse {
        val limit = (page - 1) * pageRows
        val results = reviewDao.getAllForMovieWithLimit(movieId, limit, pageRows)
        return ReviewsPageResponse(page, results, false)
    }

    suspend fun storeReviews(reviews: List<Review>) {
        reviewDao.insertAll(reviews)
    }

}