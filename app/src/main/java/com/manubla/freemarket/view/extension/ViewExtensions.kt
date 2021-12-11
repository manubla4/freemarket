package com.manubla.freemarket.extension

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
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

fun EditText.setOnSearch(callback: (String) -> Unit) {
    setOnEditorActionListener { _, actionId, event ->
        return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_SEARCH
        || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
            clearFocus()
            setText(text.toString().trim { it <= ' ' })
            callback(text.toString())
            true
        } else {
            false
        }
    }
}