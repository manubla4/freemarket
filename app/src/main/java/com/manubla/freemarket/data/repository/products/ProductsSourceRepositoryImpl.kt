package com.manubla.freemarket.data.repository.products

import com.manubla.freemarket.data.datastore.products.cloud.ProductsDataStoreCloud
import com.manubla.freemarket.data.datastore.products.database.ProductsDataStoreDatabase
import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.utils.isZero
import com.manubla.freemarket.utils.zero

class ProductsSourceRepositoryImpl(
    private val database: ProductsDataStoreDatabase,
    private val cloud: ProductsDataStoreCloud
) : ProductsSourceRepository {

    private var currentOffset: Int = Int.zero()

    override suspend fun getStoredProducts(): List<Product> =
        database.getProducts()

    override suspend fun fetchProducts(query: String): List<Product> {
        val page = cloud.fetchProductsPage(
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
        val product = cloud.fetchProductById(id)
        product?.let { safeProduct ->
            database.storeProduct(safeProduct)
            return safeProduct
        }
        return database.getProductById(id)
    }

    private fun isFirstPage(): Boolean =
        currentOffset.isZero()

}