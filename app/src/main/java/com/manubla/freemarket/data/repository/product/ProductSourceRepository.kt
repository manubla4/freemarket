package com.manubla.freemarket.data.repository.product

import com.manubla.freemarket.data.model.Product

interface ProductSourceRepository {
    suspend fun fetchProducts(query: String): List<Product>?
    suspend fun fetchProduct(id: String): Product?
}