package com.manubla.freemarket.view.alias

import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.view.adapters.DiffProducts
import com.manubla.freemarket.view.adapters.PagingProductAdapter

internal typealias PagingAdapter = PagingProductAdapter<Product>
internal typealias DiffUtil = DiffProducts<Product>