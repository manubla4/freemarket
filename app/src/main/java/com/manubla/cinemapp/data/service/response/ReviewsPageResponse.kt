package com.manubla.cinemapp.data.service.response

import android.os.Parcelable
import com.manubla.cinemapp.data.model.Review
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewsPageResponse (
    val page: Int,
    val results: List<Review>,
    var fromCloud: Boolean = true
): Parcelable