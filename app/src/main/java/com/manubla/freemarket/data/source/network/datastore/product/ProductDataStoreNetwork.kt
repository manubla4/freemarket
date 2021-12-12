package com.manubla.freemarket.data.source.network.datastore.product

import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.source.network.service.response.ProductsPageResponse

interface ProductDataStoreNetwork {
    suspend fun fetchProductById(id: String): Product?
    suspend fun fetchProductsPage(query: String, offset: Int): ProductsPageResponse?
}