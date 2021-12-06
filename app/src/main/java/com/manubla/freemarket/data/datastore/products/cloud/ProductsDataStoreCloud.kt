package com.manubla.freemarket.data.datastore.products.cloud

import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.service.response.ProductsPageResponse

interface ProductsDataStoreCloud {
    suspend fun fetchProductById(id: String): Product?
    suspend fun fetchProductsPage(query: String, offset: Int): ProductsPageResponse?
}