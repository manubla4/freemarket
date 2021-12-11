package com.manubla.freemarket.data.repository.product

import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.manager.DatabaseManager
import com.manubla.freemarket.extension.empty
import com.manubla.freemarket.extension.zero

class ProductSourceRepositoryImpl(
    private val databaseManager: DatabaseManager,
    private val database: ProductDataStoreDatabase,
    private val network: ProductDataStoreNetwork
) : ProductSourceRepository {

    private var currentOffset: Int = Int.zero()
    private var currentQuery: String = String.empty()

    override suspend fun fetchProducts(query: String): List<Product>? {
        val isNewSearch = isNewSearch(query)
        fetchNetworkPage()?.let { page ->
            val result = page.results.filter { it.hasRequiredParams() }
            val pageSize = page.results.size
            storeResult(result, isNewSearch, pageSize)
            return database.getProducts()
        }
        return if (isNewSearch) {
            database.getProducts(currentQuery)
        } else null
    }

    private suspend fun fetchNetworkPage() =
        network.fetchProductsPage(
            query = currentQuery,
            offset = currentOffset
        )

    override suspend fun fetchProduct(id: String): Product? {
        val product = network.fetchProductById(id)
        product?.let { safeProduct ->
            database.storeProduct(safeProduct)
            return safeProduct
        }
        return database.getProductById(id)
    }

    private fun isNewSearch(query: String): Boolean {
        if (currentQuery != query) {
            currentOffset = Int.zero()
            currentQuery = query
            return true
        }
        return false
    }

    private suspend fun storeResult(
        result: List<Product>,
        isNewSearch: Boolean,
        pageSize: Int
    ) {
        if (isNewSearch) {
            databaseManager.clearAllTables()
        } else {
            database.clearStorage()
        }
        database.storeProducts(result)
        currentOffset += pageSize
    }

}