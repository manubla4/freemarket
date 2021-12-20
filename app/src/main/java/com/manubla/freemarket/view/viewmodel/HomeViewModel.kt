package com.manubla.freemarket.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.data.source.network.datastore.product.ProductDataStoreNetwork
import com.manubla.freemarket.data.source.paging.PagerRequest
import com.manubla.freemarket.data.source.paging.ProductRemoteMediator
import com.manubla.freemarket.data.source.storage.datastore.product.ProductDataStoreDatabase
import com.manubla.freemarket.data.source.storage.datastore.remotekey.RemoteKeyDataStoreDatabase
import com.manubla.freemarket.data.source.storage.manager.DatabaseManager
import com.manubla.freemarket.view.callback.RemoteMediatorCallback
import com.manubla.freemarket.view.event.HomeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@ExperimentalPagingApi
class HomeViewModel(
    private val productDataStoreDatabase: ProductDataStoreDatabase,
    private val productDataStoreNetwork: ProductDataStoreNetwork,
    private val remoteKeyDataStoreDatabase: RemoteKeyDataStoreDatabase,
    private val databaseManager: DatabaseManager
) : ViewModel(), CoroutineScope, RemoteMediatorCallback {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState>
        get() = _state

    fun fetchPagingData(query: String) {
        _state.postValue(HomeState.Loading(true))
        launch(Dispatchers.IO) {
            databaseManager.clearAllTables()
            Pager(
                config = PagingConfig(
                    enablePlaceholders = false,
                    pageSize = PAGE_SIZE,
                    maxSize = PAGE_SIZE * MAX_SIZE_MULTIPLIER
                ),
                remoteMediator = ProductRemoteMediator(
                    productDataStoreDatabase = productDataStoreDatabase,
                    productDataStoreNetwork = productDataStoreNetwork,
                    remoteKeyDataStoreDatabase = remoteKeyDataStoreDatabase,
                    databaseManager = databaseManager,
                    pagerRequest = PagerRequest(query),
                    callback = this@HomeViewModel
                ),
                pagingSourceFactory = {
                    productDataStoreDatabase.getProducts()
                }
            ).flow
            .cachedIn(viewModelScope)
            .collectLatest { pagingData ->
                val modelPagingData = pagingData as PagingData<Model>
                _state.postValue(HomeState.Data(modelPagingData))
            }
        }
    }

    override fun onEmptyResult() {
        _state.postValue(HomeState.Empty)
    }

    override fun onErrorResult() {
        _state.postValue(HomeState.Error)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_SIZE_MULTIPLIER = 3
    }

}
