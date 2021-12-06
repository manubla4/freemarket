package com.manubla.freemarket.data.datastore.products.database

import com.manubla.freemarket.data.model.Product

interface ProductsDataStoreDatabase {
    suspend fun getProductById(id: String): Product?
    suspend fun getProducts(): List<Product>
    suspend fun storeProduct(product: Product)
    suspend fun storeProducts(products: List<Product>)
    suspend fun clearStorage()
}