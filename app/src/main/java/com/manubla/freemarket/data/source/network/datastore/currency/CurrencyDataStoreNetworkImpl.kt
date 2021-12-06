package com.manubla.freemarket.data.source.network.datastore.currency

import com.manubla.freemarket.data.model.Currency
import com.manubla.freemarket.data.source.network.service.CurrencyService

class CurrencyDataStoreNetworkImpl(
    private val currencyService: CurrencyService
): CurrencyDataStoreNetwork {

    override suspend fun fetchCurrencyById(id: String): Currency? {
        TODO("Not yet implemented")
    }

//    override suspend fun fetchProductById(id: String): Product? {
//        return try {
//            productService.fetchProduct(id)
//        } catch (exception: Exception) {
//            Log.e(TAG_FETCH_PRODUCT_BY_ID, Log.getStackTraceString(exception))
//            null
//        }
//    }
//
//    override suspend fun fetchProductsPage(query: String, offset: Int): ProductsPageResponse? {
//        return try {
//            productService.fetchProductsPage(
//                siteId = BuildConfig.SITE_ID,
//                query = query,
//                offset = offset,
//            )
//        } catch (exception: Exception) {
//            Log.e(TAG_FETCH_PRODUCTS_PAGE, Log.getStackTraceString(exception))
//            null
//        }
//    }

    companion object {
        private const val TAG_FETCH_CURRENCY = "fetchCurrencyById"
    }
}