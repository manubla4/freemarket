package com.manubla.freemarket.data.datastore.products.database

import android.util.Log
import com.manubla.freemarket.data.dao.ProductDao
import com.manubla.freemarket.data.model.Product

class ProductsDataStoreDatabaseImpl(
    private val productDao: ProductDao
) : ProductsDataStoreDatabase {

    override suspend fun getProductById(id: String): Product? {
        return try {
            productDao.getById(id)
        } catch (exception: Exception) {
            Log.e(TAG_GET_PRODUCT_BY_ID, Log.getStackTraceString(exception))
            null
        }
    }

    override suspend fun getProducts(): List<Product> {
        return try {
            productDao.getAll()
        } catch (exception: Exception) {
            Log.e(TAG_GET_PRODUCTS, Log.getStackTraceString(exception))
            emptyList()
        }
    }

    override suspend fun storeProduct(product: Product) {
        try {
            productDao.insert(product)
        } catch (exception: Exception) {
            Log.e(TAG_STORE_PRODUCT, Log.getStackTraceString(exception))
        }
    }

    override suspend fun storeProducts(products: List<Product>) {
        try {
            productDao.insertAll(products)
        } catch (exception: Exception) {
            Log.e(TAG_STORE_PRODUCTS, Log.getStackTraceString(exception))
        }
    }

    override suspend fun clearStorage() {
        try {
            productDao.deleteAll()
        } catch (exception: Exception) {
            Log.e(TAG_CLEAR_STORAGE, Log.getStackTraceString(exception))
        }
    }

    companion object {
        private const val TAG_GET_PRODUCT_BY_ID = "getProductById"
        private const val TAG_GET_PRODUCTS = "getProducts"
        private const val TAG_STORE_PRODUCT = "storeProduct"
        private const val TAG_STORE_PRODUCTS = "storeProducts"
        private const val TAG_CLEAR_STORAGE = "clearStorage"
    }
}