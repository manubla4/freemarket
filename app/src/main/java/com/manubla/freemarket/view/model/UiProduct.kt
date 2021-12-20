package com.manubla.freemarket.view.model

import android.content.Context
import com.manubla.freemarket.R
import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.model.business.SellerReputation
import com.manubla.freemarket.data.model.business.SellerReputation.Companion.LEVEL_TYPE_0
import com.manubla.freemarket.data.model.business.SellerReputation.Companion.LEVEL_TYPE_1
import com.manubla.freemarket.data.model.business.SellerReputation.Companion.LEVEL_TYPE_2
import com.manubla.freemarket.data.model.business.SellerReputation.Companion.LEVEL_TYPE_3
import com.manubla.freemarket.data.model.business.SellerReputation.Companion.LEVEL_TYPE_4
import com.manubla.freemarket.data.model.business.SellerReputation.Companion.LEVEL_TYPE_5
import com.manubla.freemarket.data.model.business.State
import com.manubla.freemarket.data.model.business.User
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery
import com.manubla.freemarket.extension.toNotNullable
import java.util.*

class UiProduct(product: ProductCurrencyQuery,
                user: User? = null,
                state: State? = null,
                var context: Context? = null
) {
    val title: String = product.title.toNotNullable()

    val thumbnail: String = product.thumbnail.toNotNullable()

    val displayPrice = "${product.currency ?: DEFAULT_CURRENCY} ${product.price.toNotNullable().toInt()}"

    val warranty: String = product.warranty.toNotNullable()

    val userName: String = user?.nickname.toNotNullable().toUpperCase(Locale.getDefault())

    val userAddress: String = "${user?.city}, ${state?.name}"

    val pictures: List<String> = product.pictures?.map { it.url } ?: emptyList()

    val isPlatinumUser: Boolean = user?.powerSellerStatus == SellerReputation.STATUS_TYPE_PLATINIUM

    val userLevel: Int = when {
        user?.levelId?.startsWith(LEVEL_TYPE_1.toString()).toNotNullable() -> LEVEL_TYPE_1
        user?.levelId?.startsWith(LEVEL_TYPE_2.toString()).toNotNullable() -> LEVEL_TYPE_2
        user?.levelId?.startsWith(LEVEL_TYPE_3.toString()).toNotNullable() -> LEVEL_TYPE_3
        user?.levelId?.startsWith(LEVEL_TYPE_4.toString()).toNotNullable() -> LEVEL_TYPE_4
        user?.levelId?.startsWith(LEVEL_TYPE_5.toString()).toNotNullable() -> LEVEL_TYPE_5
        else -> LEVEL_TYPE_0
    }

    val isFreeShipping: Boolean = product.freeShipping.toNotNullable()

    val condition = context?.getString(
            if (product.condition == Product.CONDITION_TYPE_NEW) {
                R.string.txt_new
            } else {
                R.string.txt_used
            }
        ).toNotNullable()

    companion object {
        internal const val DEFAULT_CURRENCY = "$"
    }
}