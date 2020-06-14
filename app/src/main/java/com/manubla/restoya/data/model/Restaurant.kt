package com.manubla.restoya.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Restaurant.TABLE_NAME)
data class Restaurant (
    @PrimaryKey val id: Int,
    val name: String,
    val logo: String,
    val coordinates: String,
    val distance: Double,
    val discount: Int,
    @ColumnInfo(name = "general_score") val generalScore: Double,
    @ColumnInfo(name = "has_online_payment_methods") val hasOnlinePaymentMethods: Boolean,
    @ColumnInfo(name = "delivery_time_min_minutes") val deliveryTimeMinMinutes: Int?,
    @ColumnInfo(name = "delivery_time_max_minutes") val deliveryTimeMaxMinutes: Int?
): Parcelable {

    companion object {
        const val TABLE_NAME = "restaurants"
    }
}