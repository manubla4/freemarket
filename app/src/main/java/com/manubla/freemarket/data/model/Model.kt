package com.manubla.freemarket.data.model

import android.util.Log
import com.manubla.freemarket.extension.isNull

abstract class Model {

    abstract val requiredParams: Map<String, Any?>

    abstract fun assembleTableAndParam(param: String): String

    fun hasRequiredParams(): Boolean {
        val nullParams = requiredParams
            .filter { it.value.isNull() }
            .map { it.key }
        if (nullParams.isNotEmpty()) {
            val nullParamName = nullParams.first()
            val tableAndParam = assembleTableAndParam(nullParamName)
            val exception = IllegalArgumentException("$tableAndParam is required - object not rendered")
            Log.e(TAG_REQUIRED_PARAMS, Log.getStackTraceString(exception))
            return false
        }
        return true
    }

    fun areContentsTheSame(newItem: Model): Boolean {
        return this == newItem
    }

    companion object {
        private const val TAG_REQUIRED_PARAMS = "hasRequiredParams"
    }
}