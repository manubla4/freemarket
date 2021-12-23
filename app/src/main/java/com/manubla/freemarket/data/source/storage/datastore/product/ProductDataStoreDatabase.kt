package com.manubla.freemarket.data.source.storage.datastore.product

import androidx.paging.PagingSource
import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery
import com.manubla.freemarket.extension.empty

interface ProductDataStoreDatabase {
    fun getProducts(): PagingSource<Int, ProductCurrencyQuery>
    suspend fun getProductById(id: String): ProductCurrencyQuery?
    suspend fun updateProduct(product: Product)
    suspend fun storeProduct(product: Product)
    suspend fun storeProducts(products: List<Product>)
    suspend fun clearStorage()
}