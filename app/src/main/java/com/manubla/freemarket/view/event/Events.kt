package com.manubla.freemarket.view.event

import androidx.paging.PagingData
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.data.model.business.Product

sealed class SplashState {
    object Done: SplashState()
}

sealed class HomeState {
    object Loading: HomeState()
    data class Data(val pagingData: PagingData<Model>): HomeState()
}

sealed class DetailState {
    data class Data(val pagingData: Product): DetailState()
}