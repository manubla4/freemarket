package com.manubla.freemarket.data.source.network.datastore.state

import com.manubla.freemarket.data.model.State
import com.manubla.freemarket.data.source.network.service.StateService

class StateDataStoreNetworkImpl(
    private val stateService: StateService
): StateDataStoreNetwork {

    override suspend fun fetchStateById(id: String): State? {
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
        private const val TAG_FETCH_STATE = "fetchStateById"
    }
}