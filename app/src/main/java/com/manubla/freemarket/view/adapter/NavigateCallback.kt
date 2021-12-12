package com.manubla.freemarket.view.adapter

interface NavigateCallback {
    fun onNavigate(destination: String, data: String)
    companion object {
        const val DESTINATION_DETAIL = "DETAIL"
    }
}