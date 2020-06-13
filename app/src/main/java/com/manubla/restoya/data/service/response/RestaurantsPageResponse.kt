package com.manubla.restoya.data.service.response

import android.os.Parcelable
import com.manubla.restoya.data.model.Restaurant
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestaurantsPageResponse (
    val page: Int,
    val results: List<Restaurant>,
    var fromCloud: Boolean = true
): Parcelable