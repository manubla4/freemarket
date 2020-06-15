package com.manubla.restoya.presentation.helper

import android.view.View
import android.widget.EditText
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visibleIf(condition: Boolean) {
    if (condition) visible() else invisible()
}

fun SwipeRefreshLayout.enableAndRefresh(condition: Boolean) {
    if (condition) {
        isEnabled = true
        isRefreshing = true
    }
    else {
        isRefreshing = false
        isEnabled = false
    }
}

fun EditText.editTextString() = editableText.toString()
