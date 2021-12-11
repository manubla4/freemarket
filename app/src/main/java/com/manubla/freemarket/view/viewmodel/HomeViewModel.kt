package com.manubla.freemarket.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.manubla.freemarket.data.repository.product.ProductSourceRepository
import com.manubla.freemarket.view.event.HomeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val productSourceRepository: ProductSourceRepository
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState>
        get() = _state

    fun fetchPagingData() {
        _state.postValue(HomeState.Loading)
        launch(Dispatchers.IO) {

//            Pager(
//                config = PagingConfig(
//                    initialLoadSize = NETWORK_INITIAL_PAGE_SIZE,
//                    enablePlaceholders = false,
//                    pageSize = NETWORK_PAGE_SIZE,
//                    prefetchDistance = PREFETCH_DISTANCE
//                )
//            ) {
//                ShopListPagingSource(
//                    shopListRemoteDataSource,
//                    shopListLocalDataSource,
//                    loggingHandler,
//                    pagerRequest.copy(
//                        businessType = businessType,
//                        commandId = commandId,
//                        searchQuery = searchQuery,
//                        selectedFilters = selectedFilters,
//                        hash = hash
//                    ),
//                )
//            }.flow
//
//            .cachedIn(viewModelScope)
//            .collectLatest { pagingComponent ->
//                postResults(
//                    arguments,
//                    pagingComponent,
//                    header,
//                    footer
//                )
//            }
        }

    }
}