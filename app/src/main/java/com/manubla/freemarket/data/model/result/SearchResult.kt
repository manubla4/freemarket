package com.manubla.freemarket.data.model.result

import android.os.Parcelable
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.extension.empty
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResult (
    val type: String
): Parcelable, Model() {

    override fun requiredParams() = mapOf<String,Any>()

    override fun areContentsTheSame(newItem: Model) = false

    override fun assembleEntityAndParam(param: String): String
        = String.empty()

    companion object {
        internal const val STATE_ERROR = "error"
        internal const val STATE_EMPTY = "empty"
    }
}