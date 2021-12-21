package com.manubla.freemarket.view.callback

import com.manubla.freemarket.view.enum.Destination

interface NavigateCallback {
    fun onNavigate(destination: Destination, data: String)
}