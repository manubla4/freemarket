package com.manubla.freemarket.extension

fun String.Companion.empty() = ""
fun Char.Companion.space() = ' '

fun String?.toNotNullable(): String {
    return this ?: String.empty()
}