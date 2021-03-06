package com.manubla.freemarket.data.model.business

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.manubla.freemarket.extension.toNotNullable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellerReputation (

    @ColumnInfo(name = PARAM_LEVEL_ID)
    @SerializedName(PARAM_LEVEL_ID)
    private val _levelId: String?,

    @ColumnInfo(name = PARAM_POWER_SELLER_STATUS)
    @SerializedName(PARAM_POWER_SELLER_STATUS)
    private val _powerSellerStatus: String?

): Parcelable {

    val levelId: String
        get() = _levelId.toNotNullable()

    val powerSellerStatus: String
        get() = _powerSellerStatus.toNotNullable()

    companion object {
        internal const val PARAM_LEVEL_ID = "level_id"
        internal const val PARAM_POWER_SELLER_STATUS = "power_seller_status"
        internal const val STATUS_TYPE_PLATINIUM = "platinum"
    }
}