package com.manubla.freemarket.extension

fun String.Companion.empty() = ""

fun String?.toNotNullable(): String {
    return this ?: String.empty()
}
