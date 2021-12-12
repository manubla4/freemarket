package com.manubla.freemarket.data.model.business

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.extension.toNotNullable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shipping (

@ColumnInfo(name = PARAM_FREE_SHIPPING)
@SerializedName(PARAM_FREE_SHIPPING)
private val _freeShipping: Boolean?,

): Parcelable {

    val freeShipping: Boolean
    get() = _freeShipping.toNotNullable()

    companion object {
        internal const val PARAM_FREE_SHIPPING = "free_shipping"
    }
}