package com.manubla.cinemapp.presentation.helper

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

fun View.visibleIf(condition: Boolean) {
    if (condition) visible() else gone()
}

fun EditText.editTextString() = editableText.toString()
