package com.manubla.cinemapp.presentation.util

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.manubla.cinemapp.R
import java.lang.ref.WeakReference

private const val LENGTH_SHORT = 0
private const val LENGTH_LONG = 1

private const val STATE_NORMAL = 0
private const val STATE_SUCCESS = 1
private const val STATE_ERROR = 2

private var mSnackbar: WeakReference<Snackbar> = WeakReference<Snackbar>(null)


private fun showMessage(message: String, view: View?, context: Context?, length: Int, state: Int) {
    val snackbarPrev = mSnackbar.get()
    if (snackbarPrev != null && snackbarPrev.isShown)
        snackbarPrev.dismiss()

    if (view != null && context != null) {
        val snackbar = Snackbar.make(view, message,
            (if (length == LENGTH_SHORT) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG))
        snackbar.apply {
            setAction("OK") { dismiss() }
            if (state == STATE_SUCCESS) {
                getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorSuccess))
                setActionTextColor(Color.WHITE)
            }
            if (state == STATE_ERROR) {
                getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorFailure))
                setActionTextColor(Color.WHITE)
            }
            show()
            mSnackbar = WeakReference(this)
        }
    }
}

fun showLongMessage(message: String, view: View?, context: Context?) {
    showMessage(message, view, context, LENGTH_LONG, STATE_NORMAL)
}

fun showShortMessage(message: String, view: View?, context: Context?) {
    showMessage(message, view, context, LENGTH_SHORT, STATE_NORMAL)
}

fun showLongSuccessMessage(message: String, view: View?, context: Context?) {
    showMessage(message, view, context, LENGTH_LONG, STATE_SUCCESS)
}

fun showShortSuccessMessage(message: String, view: View?, context: Context?) {
    showMessage(message, view, context, LENGTH_SHORT, STATE_SUCCESS)
}

fun showLongErrorMessage(message: String, view: View?, context: Context?) {
    showMessage(message, view, context, LENGTH_LONG, STATE_ERROR)
}

fun showShortErrorMessage(message: String, view: View?, context: Context?) {
    showMessage(message, view, context, LENGTH_SHORT, STATE_ERROR)
}
