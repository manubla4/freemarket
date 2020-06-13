package com.manubla.restoya.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Restaurant.TABLE_NAME)
data class Restaurant (
    @PrimaryKey var id: Int,
    var name: String,
    var logo: String,
    var coordinates: String,
    var distance: Double,
    var discount: Int,
    @ColumnInfo(name = "payment_methods") var paymentMethods: String,
    @ColumnInfo(name = "general_score") var generalScore: Double,
    @ColumnInfo(name = "all_categories") var allCategories: String,
    @ColumnInfo(name = "business_type") var businessType: String,
    @ColumnInfo(name = "has_online_payment_methods") var hasOnlinePaymentMethods: Boolean,
    @ColumnInfo(name = "delivery_time_min_minutes") var deliveryTimeMinMinutes: Int?,
    @ColumnInfo(name = "delivery_time_max_minutes") var deliveryTimeMaxMinutes: Int?
): Parcelable {

    companion object {
        const val TABLE_NAME = "restaurants"
    }
}