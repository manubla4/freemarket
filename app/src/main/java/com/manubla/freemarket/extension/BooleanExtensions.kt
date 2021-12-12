package com.manubla.freemarket.extension

fun Boolean?.toNotNullable()
    = this ?: false
