package com.manubla.freemarket.data.model

import com.manubla.freemarket.utils.isNull

abstract class Model {

    abstract val requiredParams: Map<String, Any?>

    abstract fun assembleTableAndParam(param: String): String

    fun check() {
        val nullParams = requiredParams
            .filter { it.value.isNull() }
            .map { it.key }
        if (nullParams.isNotEmpty()) {
            val nullParamName = nullParams.first()
            val tableAndParam = assembleTableAndParam(nullParamName)
            throw IllegalArgumentException("$tableAndParam is required")
        }
    }
}