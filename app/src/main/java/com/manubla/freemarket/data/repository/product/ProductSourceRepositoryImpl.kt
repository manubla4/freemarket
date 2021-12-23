package com.manubla.freemarket.data.repository.product

import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery

class ProductSourceRepositoryImpl(
    private val database: ProductDataStoreDatabase,
    private val network: ProductDataStoreNetwork
) : ProductSourceRepository {

    override suspend fun fetchProduct(id: String): ProductCurrencyQuery? {
        val product = network.fetchProductById(id)
        product?.let { safeProduct ->
            database.updateProduct(safeProduct)
        }
        return database.getProductById(id)
    }
}