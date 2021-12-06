package com.manubla.freemarket.data.repository.product

import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetwork
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.utils.isZero
import com.manubla.freemarket.utils.zero

class ProductSourceRepositoryImpl(
    private val database: ProductDataStoreDatabase,
    private val network: ProductDataStoreNetwork
) : ProductSourceRepository {

    private var currentOffset: Int = Int.zero()

    override suspend fun fetchLocalProducts(): List<Product> =
        database.getProducts()

    override suspend fun fetchProducts(query: String): List<Product> {
        val page = network.fetchProductsPage(
            query = query,
            offset = currentOffset
        )
        page?.let { safePage ->
            val result = safePage.results.filter { it.hasRequiredParams() }
            if (isFirstPage()) {
                database.clearStorage()
                database.storeProducts(result)
            }
            currentOffset += safePage.results.size
            return result
        }
        return emptyList()
    }

    override suspend fun fetchProduct(id: String): Product? {
        val product = network.fetchProductById(id)
        product?.let { safeProduct ->
            database.storeProduct(safeProduct)
            return safeProduct
        }
        return database.getProductById(id)
    }

    private fun isFirstPage(): Boolean =
        currentOffset.isZero()

}