package com.manubla.freemarket.data.source.network.service.response

import android.os.Parcelable
import com.manubla.freemarket.data.model.business.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductsPageResponse (
    val query: String,
    val paging: PagingData,
    val results: List<Product>
): Parcelable