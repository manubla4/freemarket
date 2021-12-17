package com.manubla.freemarket.data.repository.product

import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.manager.DatabaseManager
import com.manubla.freemarket.extension.empty

class ProductSourceRepositoryImpl(
    private val databaseManager: DatabaseManager,
    private val database: ProductDataStoreDatabase,
    private val network: ProductDataStoreNetwork
) : ProductSourceRepository {

    private var currentQuery: String = String.empty()

    override suspend fun fetchData(query: String, page: Int, pageSize: Int): List<Model>? {
        val isNewSearch = isNewSearch(query)
        fetchNetworkPage(page, pageSize)?.let { pageResult ->
            val result = pageResult.results.filter { it.hasRequiredParams() }
            storeResult(result, isNewSearch)
            return database.getProducts()
        }
        return if (isNewSearch) {
            database.getProducts(currentQuery)
        } else null
    }

    private suspend fun fetchNetworkPage(page: Int, pageSize: Int) =
        network.fetchProductsPage(
            query = currentQuery,
            offset = page * pageSize,
            pageSize = pageSize
        )

    override suspend fun fetchProduct(id: String): Product? {
        val product = network.fetchProductById(id)
        product?.let { safeProduct ->
            database.storeProduct(safeProduct)
        }
        return database.getProductById(id)
    }

    private fun isNewSearch(query: String): Boolean {
        if (currentQuery != query) {
            currentQuery = query
            return true
        }
        return false
    }

    private suspend fun storeResult(
        result: List<Product>,
        isNewSearch: Boolean
    ) {
        if (isNewSearch) {
            databaseManager.clearAllTables()
        } else {
            database.clearStorage()
        }
        database.storeProducts(result)
    }

}