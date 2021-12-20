package com.manubla.freemarket.data.source.storage.query

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.data.model.business.Picture
import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.model.business.Shipping
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductCurrencyQuery (
    val id: String,
    val title: String?,
    val price: Double?,
    val condition: String?,
    val thumbnail: String?,
    val warranty: String?,
    val currency: String?,
    val pictures: List<Picture>?,
    @ColumnInfo(name = Shipping.PARAM_FREE_SHIPPING)
    val freeShipping: Boolean?,
    @ColumnInfo(name = Product.PARAM_SELLER_ID)
    val sellerId: Long?
): Parcelable, Model() {

    override fun requiredParams() = mapOf(
        Pair(Product.PARAM_ID, id),
        Pair(Product.PARAM_TITLE, title),
        Pair(Product.PARAM_PRICE, price)
    )

    override fun areContentsTheSame(newItem: Model): Boolean {
        val newProduct = newItem as? ProductCurrencyQuery
        return newProduct?.let {
            this.id == it.id && this.title == it.title
        } ?: false
    }

    override fun assembleEntityAndParam(param: String): String
        = "$ENTITY_NAME - $param"

    companion object {
        internal const val ENTITY_NAME = "ProductCurrencyQuery"
    }
}