package com.manubla.freemarket.utils

import android.view.View
import android.widget.EditText

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.invisibleIf(condition: Boolean) {
    if (condition) invisible() else visible()
}

fun View.visibleIf(condition: Boolean) {
    if (condition) visible() else invisible()
}

fun EditText.editTextString() = editableText.toString()
