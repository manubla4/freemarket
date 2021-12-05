package com.manubla.freemarket.data.service.response

import android.os.Parcelable
import com.manubla.freemarket.data.model.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantsPageResponse (
    val total: Int?,
    val max: Int,
    val sort: String,
    val count: Int,
    val data: List<Product>,
    val offset: Int
): Parcelable