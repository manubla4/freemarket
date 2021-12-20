package com.manubla.freemarket.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manubla.freemarket.data.repository.product.ProductSourceRepository
import com.manubla.freemarket.data.repository.state.StateSourceRepository
import com.manubla.freemarket.data.repository.user.UserSourceRepository
import com.manubla.freemarket.extension.toNotNullable
import com.manubla.freemarket.view.event.DetailState
import com.manubla.freemarket.view.model.UiProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(
    private val productSourceRepository: ProductSourceRepository,
    private val userSourceRepository: UserSourceRepository,
    private val stateSourceRepository: StateSourceRepository
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main

    private val _state = MutableLiveData<DetailState>()
    val state: LiveData<DetailState>
        get() = _state

    fun fetchProduct(id: String) {
        _state.postValue(DetailState.Loading(true))
        launch(Dispatchers.IO) {
            val product = productSourceRepository.fetchProduct(id)
            product?.let { safeProduct ->
                val user = userSourceRepository.fetchUser(safeProduct.sellerId.toNotNullable())
                val state = stateSourceRepository.fetchState(user?.stateId.toNotNullable())
                val uiProduct = UiProduct(
                    product = product,
                    user = user,
                    state = state
                )
                _state.postValue(DetailState.Data(uiProduct))
            } ?: _state.postValue(DetailState.Error)
            _state.postValue(DetailState.Loading(false))
        }
    }

}