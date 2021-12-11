package com.manubla.freemarket.view.extension

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.manubla.freemarket.extension.space

private const val MIN_LENGTH_QUERY_TEXT = 2

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
        val trimmedText = text.toString().trim { it <= Char.space() }
        return@setOnEditorActionListener if (isSearchAction(actionId, event)
        && isAcceptableInput(trimmedText)) {
            clearFocus()
            setText(trimmedText)
            callback(text.toString())
            true
        } else {
            false
        }
    }
}

private fun isSearchAction(actionId: Int?, event: KeyEvent?) =
    actionId == EditorInfo.IME_ACTION_SEARCH
            || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER

private fun isAcceptableInput(text: String) =
    text.isNotBlank() && text.length >= MIN_LENGTH_QUERY_TEXT