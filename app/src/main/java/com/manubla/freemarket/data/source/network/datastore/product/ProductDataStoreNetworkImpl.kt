package com.manubla.freemarket.data.source.network.datastore.product

import android.util.Log
import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.source.network.service.ProductService
import com.manubla.freemarket.data.source.network.service.response.ProductsPageResponse

class ProductDataStoreNetworkImpl(
    private val productService: ProductService
): ProductDataStoreNetwork {

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