package com.manubla.freemarket.data.repository.product

import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery

interface ProductSourceRepository {
    suspend fun fetchProduct(id: String): ProductCurrencyQuery?
}