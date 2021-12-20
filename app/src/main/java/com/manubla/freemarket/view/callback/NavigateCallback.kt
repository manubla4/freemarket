package com.manubla.freemarket.view.callback

interface NavigateCallback {
    fun onNavigate(destination: String, data: String)
    companion object {
        const val DESTINATION_DETAIL = "DETAIL"
    }
}