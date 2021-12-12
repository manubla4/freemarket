package com.manubla.freemarket.data.repository.product

import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.repository.base.SourceRepository

interface ProductSourceRepository: SourceRepository {
    suspend fun fetchProduct(id: String): Product?
}