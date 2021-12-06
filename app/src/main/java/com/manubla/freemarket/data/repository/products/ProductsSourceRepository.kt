package com.manubla.freemarket.data.repository.products

import com.manubla.freemarket.data.model.Product

interface ProductsSourceRepository {
    suspend fun getStoredProducts(): List<Product>
    suspend fun fetchProducts(query: String): List<Product>
    suspend fun fetchProduct(id: String): Product?
}