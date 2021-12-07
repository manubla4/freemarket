package com.manubla.freemarket.data.source.storage.datastore.product

import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.utils.empty

interface ProductDataStoreDatabase {
    suspend fun getProductById(id: String): Product?
    suspend fun getProducts(query: String = String.empty()): List<Product>
    suspend fun storeProduct(product: Product)
    suspend fun storeProducts(products: List<Product>)
    suspend fun clearStorage()
}