package com.manubla.freemarket.utils

fun String.Companion.empty() = ""

fun String?.toNotNullable(): String {
    return this ?: String.empty()
}
