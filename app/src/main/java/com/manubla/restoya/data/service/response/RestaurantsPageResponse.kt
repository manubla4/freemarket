package com.manubla.restoya.data.service.response

import android.os.Parcelable
import com.manubla.restoya.data.model.Restaurant
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestaurantsPageResponse (
    val total: Int?,
    val max: Int,
    val sort: String,
    val count: Int,
    val data: List<Restaurant>,
    val offset: Int
): Parcelable