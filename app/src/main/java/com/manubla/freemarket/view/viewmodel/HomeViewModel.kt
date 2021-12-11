package com.manubla.freemarket.view.viewmodel

import androidx.lifecycle.ViewModel
import com.manubla.freemarket.data.repository.product.ProductSourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val productSourceRepository: ProductSourceRepository
) : ViewModel(), CoroutineScope {

    fun fetchPagingComponent() {
        TODO("Not yet implemented")
    }

    fun refresh() {
        TODO("Not yet implemented")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    
}