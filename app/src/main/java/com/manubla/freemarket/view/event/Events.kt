package com.manubla.freemarket.view.event

import androidx.paging.PagingData
import com.manubla.freemarket.data.model.Product

sealed class SplashState {
    object Done: SplashState()
}

sealed class HomeState {
    object Loading: HomeState()
    data class Data(val pagingData: PagingData<Product>): HomeState()
}

sealed class DetailState {
    data class Data(val pagingData: Product): DetailState()
}