package com.manubla.freemarket.view.viewmodel

import androidx.lifecycle.ViewModel
import com.manubla.freemarket.data.repository.product.ProductSourceRepository
import com.manubla.freemarket.data.repository.state.StateSourceRepository
import com.manubla.freemarket.data.repository.user.UserSourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DetailViewModel(
    private val productSourceRepository: ProductSourceRepository,
    private val userSourceRepository: UserSourceRepository,
    private val stateSourceRepository: StateSourceRepository
) : ViewModel(), CoroutineScope {

        override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}