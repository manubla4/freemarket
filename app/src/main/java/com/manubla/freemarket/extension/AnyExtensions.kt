package com.manubla.freemarket.extension

fun Any?.notNull(): Boolean {
    return this != null
}

fun Any?.isNull(): Boolean {
    return this == null
}
