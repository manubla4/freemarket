package com.manubla.freemarket.view.util

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

private const val TAG_HIDE_KEYBOARD = "hideKeyboard"

fun hideKeyboard(context: Context?, view: View) {
    try {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    } catch (e: Exception) {
        Log.e(TAG_HIDE_KEYBOARD, Log.getStackTraceString(e))
    }
}