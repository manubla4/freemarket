package com.manubla.freemarket.utils

fun Long.Companion.zero() = 0L
fun Double.Companion.zero() = 0.0
fun Int.Companion.zero() = 0


fun Long?.toNotNullable(): Long {
    return this ?: Long.zero()
}

fun Int?.toNotNullable(): Int {
    return this ?: Int.zero()
}