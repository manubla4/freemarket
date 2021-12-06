package com.manubla.freemarket.data.datastore.products.cloud

import android.util.Log
import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.service.ProductService
import com.manubla.freemarket.data.service.response.ProductsPageResponse

class ProductsDataStoreCloudImpl(
    private val productService: ProductService
): ProductsDataStoreCloud {

    override suspend fun fetchProductById(id: String): Product? {
        return try {
            productService.fetchProduct(id)
        } catch (exception: Exception) {
            Log.e(TAG_FETCH_PRODUCT_BY_ID, Log.getStackTraceString(exception))
            null
        }
    }

    override suspend fun fetchProductsPage(query: String, offset: Int): ProductsPageResponse? {
        return try {
            productService.fetchProductsPage(
                siteId = BuildConfig.SITE_ID,
                query = query,
                offset = offset,
            )
        } catch (exception: Exception) {
            Log.e(TAG_FETCH_PRODUCTS_PAGE, Log.getStackTraceString(exception))
            null
        }
    }

    companion object {
        private const val TAG_FETCH_PRODUCT_BY_ID = "fetchProductById"
        private const val TAG_FETCH_PRODUCTS_PAGE = "fetchProductsPage"
    }

}