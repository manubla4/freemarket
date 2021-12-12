package com.manubla.freemarket.data.source.storage.datastore.products

import com.manubla.freemarket.data.model.business.Product

interface ProductsDataStoreDatabase {
    suspend fun getProductById(id: String): Product?
    suspend fun getProducts(): List<Product>
    suspend fun storeProduct(product: Product)
    suspend fun storeProducts(products: List<Product>)
    suspend fun clearStorage()
}