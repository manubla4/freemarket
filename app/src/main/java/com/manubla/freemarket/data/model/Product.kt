package com.manubla.freemarket.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Product.TABLE_NAME)
data class Product (
    @PrimaryKey val id: Int,
    val name: String,
    val logo: String,
    val coordinates: String,
    val distance: Double,
    val discount: Int,
    @ColumnInfo(name = "general_score") @SerializedName("generalScore") val generalScore: Double,
    @ColumnInfo(name = "has_online_payment_methods") @SerializedName("hasOnlinePaymentMethods") val hasOnlinePaymentMethods: Boolean,
    @ColumnInfo(name = "delivery_time_min_minutes") @SerializedName("deliveryTimeMinMinutes") val deliveryTimeMinMinutes: Int?,
    @ColumnInfo(name = "delivery_time_max_minutes") @SerializedName("deliveryTimeMaxMinutes") val deliveryTimeMaxMinutes: Int?
): Parcelable {

    companion object {
        internal const val TABLE_NAME = "products"
    }
}