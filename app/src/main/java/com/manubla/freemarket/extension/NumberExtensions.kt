package com.manubla.freemarket.extension

fun Long.Companion.zero() = 0L
fun Double.Companion.zero() = 0.0
fun Int.Companion.zero() = 0


fun Double?.toNotNullable(): Double {
    return this ?: Double.zero()
}

fun Long?.toNotNullable(): Long {
    return this ?: Long.zero()
}

fun Int?.toNotNullable(): Int {
    return this ?: Int.zero()
}

fun Int?.isZero(): Boolean {
    return this == Int.zero()
}