package com.manubla.freemarket.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.manubla.freemarket.data.repository.product.ProductSourceRepository
import com.manubla.freemarket.data.source.network.service.ProductService.Companion.PAGE_LIMIT
import com.manubla.freemarket.data.source.paging.ModelPagingSource
import com.manubla.freemarket.data.source.paging.PagerRequest
import com.manubla.freemarket.view.event.HomeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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

    fun fetchPagingData(query: String) {
        _state.postValue(HomeState.Loading(true))
        launch(Dispatchers.IO) {
            Pager(
                config = PagingConfig(
                    initialLoadSize = INITIAL_PAGE_SIZE,
                    enablePlaceholders = false,
                    pageSize = PAGE_SIZE,
                    prefetchDistance = PREFETCH_DISTANCE
                )
            ) {
                ModelPagingSource(
                    dataSource = productSourceRepository,
                    pagerRequest = PagerRequest(query)
                )
            }.flow
            .cachedIn(viewModelScope)
            .collectLatest { pagingData ->
                _state.postValue(HomeState.Data(pagingData))
            }
        }
    }

    companion object {
        private const val INITIAL_PAGE_SIZE = 60
        private const val PAGE_SIZE = 50
        private const val PREFETCH_DISTANCE = 4
    }

}