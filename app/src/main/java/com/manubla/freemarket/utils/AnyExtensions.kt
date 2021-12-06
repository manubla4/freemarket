package com.manubla.freemarket.utils

fun Any?.notNull(): Boolean {
    return this != null
}

fun Any?.isNull(): Boolean {
    return this == null
}
