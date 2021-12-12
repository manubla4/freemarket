package com.manubla.freemarket.view.event

import androidx.paging.PagingData
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.view.model.UiProduct

sealed class SplashState {
    object Done: SplashState()
}

sealed class HomeState {
    data class Loading(val loading: Boolean): HomeState()
    data class Data(val pagingData: PagingData<Model>): HomeState()
}

sealed class DetailState {
    object Error: DetailState()
    data class Loading(val loading: Boolean): DetailState()
    data class Data(val uiProduct: UiProduct): DetailState()
}