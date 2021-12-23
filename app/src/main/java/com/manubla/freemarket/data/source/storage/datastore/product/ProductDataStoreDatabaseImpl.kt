package com.manubla.freemarket.data.source.storage.datastore.product

import android.util.Log
import androidx.paging.PagingSource
import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.source.storage.dao.ProductDao
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery

class ProductDataStoreDatabaseImpl(
    private val productDao: ProductDao
) : ProductDataStoreDatabase {

    override suspend fun getProductById(id: String): ProductCurrencyQuery? {
        return try {
            productDao.getById(id)
        } catch (exception: Exception) {
            Log.e(TAG_GET_PRODUCT_BY_ID, Log.getStackTraceString(exception))
            null
        }
    }

    override fun getProducts(): PagingSource<Int, ProductCurrencyQuery> {
        return productDao.getPagingSource()
    }

    override suspend fun storeProduct(product: Product) {
        try {
            productDao.insert(product)
        } catch (exception: Exception) {
            Log.e(TAG_STORE_PRODUCT, Log.getStackTraceString(exception))
        }
    }

    override suspend fun updateProduct(product: Product) {
        try {
            productDao.update(product.id,
                product.pictures,
                product.warranty,
                product.sellerId
            )
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
        private const val TAG_STORE_PRODUCT = "storeProduct"
        private const val TAG_STORE_PRODUCTS = "storeProducts"
        private const val TAG_CLEAR_STORAGE = "clearStorage"
    }
}