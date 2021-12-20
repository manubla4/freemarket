package com.manubla.freemarket.data.source.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetwork
import com.manubla.freemarket.data.source.network.service.response.ProductsPageResponse
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.remotekey.RemoteKeyDataStoreDatabase
import com.manubla.freemarket.data.source.storage.entity.RemoteKey
import com.manubla.freemarket.data.source.storage.manager.DatabaseManager
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery
import com.manubla.freemarket.view.callback.RemoteMediatorCallback

@ExperimentalPagingApi
class ProductRemoteMediator(
    private val productDataStoreDatabase: ProductDataStoreDatabase,
    private val productDataStoreNetwork: ProductDataStoreNetwork,
    private val remoteKeyDataStoreDatabase: RemoteKeyDataStoreDatabase,
    private val databaseManager: DatabaseManager,
    private val pagerRequest: PagerRequest,
    private val callback: RemoteMediatorCallback
) : RemoteMediator<Int, ProductCurrencyQuery>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductCurrencyQuery>
    ): MediatorResult {
        val currentPage: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKey = getLastRemoteKey(state)
                val nextKey = remoteKey?.nextKey
                nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
            }
            LoadType.PREPEND -> {
                val remoteKey = getFirstRemoteKey(state)
                val prevKey = remoteKey?.prevKey
                prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
            }
        }

        return try {
            val pageSize = state.config.pageSize
            val offset = currentPage * pageSize
            val response = productDataStoreNetwork.fetchProductsPage(
                query = pagerRequest.query,
                offset = offset,
                pageSize = pageSize
            )
            processResponse(state, response, loadType, currentPage)
        } catch (exception: Exception) {
            if (isFirstPage(loadType, currentPage, state)) {
                callback.onErrorResult()
            }
            MediatorResult.Error(exception)
        }
    }

    private suspend fun processResponse(
        state: PagingState<Int, ProductCurrencyQuery>,
        response: ProductsPageResponse?,
        loadType: LoadType,
        currentPage: Int
    ): MediatorResult.Success {
        response?.let { safeResponse ->
            val result = safeResponse.results.filter { it.hasRequiredParams() }
            val isEndOfList = result.isEmpty()
            if (result.isEmpty() && isFirstPage(loadType, currentPage, state)) {
                callback.onEmptyResult()
            }
            if (loadType == LoadType.REFRESH) {
                databaseManager.clearAllTables()
            }
            val prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1
            val nextKey = if (isEndOfList) null else currentPage + 1
            val keys = result.map { product ->
                RemoteKey(
                    id = product.id,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            remoteKeyDataStoreDatabase.storeKeys(keys)
            productDataStoreDatabase.storeProducts(safeResponse.results)
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } ?: throw Exception(TAG_LOAD_PRODUCT_PAGE)
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ProductCurrencyQuery>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { product ->
                remoteKeyDataStoreDatabase.getKeyById(product.id)
            }
        }
    }

    private suspend fun getLastRemoteKey(
        state: PagingState<Int, ProductCurrencyQuery>
    ): RemoteKey? {
        return state.lastItemOrNull()?.let { product ->
            remoteKeyDataStoreDatabase.getKeyById(product.id)
        }
    }

    private suspend fun getFirstRemoteKey(
        state: PagingState<Int, ProductCurrencyQuery>
    ): RemoteKey? {
        return state.firstItemOrNull()?.let { product ->
            remoteKeyDataStoreDatabase.getKeyById(product.id)
        }
    }

    private fun isFirstPage(
        loadType: LoadType,
        currentPage: Int,
        state: PagingState<Int, ProductCurrencyQuery>
    ) = state.pages.isEmpty()
            && loadType == LoadType.REFRESH
            && currentPage == STARTING_PAGE_INDEX

    companion object {
        private const val STARTING_PAGE_INDEX = 0
        private const val TAG_LOAD_PRODUCT_PAGE = "LOAD_PRODUCT_PAGE"
    }

}