package com.manubla.cinemapp.presentation.view.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.cinemapp.data.model.Review
import com.manubla.cinemapp.data.repository.reviews.ReviewsSourceRepository
import com.manubla.cinemapp.data.service.response.ReviewsPageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ReviewViewModel(private val reviewsRepository: ReviewsSourceRepository)
    : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val reviews: LiveData<ReviewsPageResponse>
        get() = localReviews

    private val localReviews = MutableLiveData<ReviewsPageResponse>()

    fun loadReviews(movieId: Int, page: Int) {
        launch(Dispatchers.IO) {
            val reviewsPage = getReviews(movieId, page)
            for (review in reviewsPage.results) {
                review.movieId = movieId
            }
            storeReviews(reviewsPage.results)
            localReviews.postValue(reviewsPage)
        }
    }


    private suspend fun getReviews(movieId: Int, page: Int): ReviewsPageResponse =
        try {
            reviewsRepository.getReviewsPage(movieId, page)
        } catch (error: Exception) {
            ReviewsPageResponse(1, listOf(), false)
        }

    private suspend fun storeReviews(reviews: List<Review>) {
        try {
            reviewsRepository.storeReviews(reviews)
        } catch (ignored: Exception) {
        }
    }

}