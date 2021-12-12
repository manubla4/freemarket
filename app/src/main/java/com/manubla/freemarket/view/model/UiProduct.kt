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
import com.manubla.freemarket.extension.empty
import com.manubla.freemarket.extension.toNotNullable

class UiProduct(product: Product,
                user: User? = null,
                state: State? = null,
                context: Context? = null
) {
    val title: String = product.title

    val thumbnail: String = product.thumbnail

    val displayPrice = "${product.currency} ${product.price}"

    val warranty: String = product.warranty

    val userName: String = user?.nickname.toNotNullable()

    val userAddress: String = "${user?.city}, ${state?.name}"

    val userStatus: String =
        if (user?.powerSellerStatus == SellerReputation.STATUS_TYPE_PLATINIUM) {
            context?.getString(R.string.txt_platinum_user).toNotNullable()
        } else {
            String.empty()
        }

    val userLevel: Int = when {
        user?.levelId?.startsWith(LEVEL_TYPE_1.toString()).toNotNullable() -> LEVEL_TYPE_1
        user?.levelId?.startsWith(LEVEL_TYPE_2.toString()).toNotNullable() -> LEVEL_TYPE_2
        user?.levelId?.startsWith(LEVEL_TYPE_3.toString()).toNotNullable() -> LEVEL_TYPE_3
        user?.levelId?.startsWith(LEVEL_TYPE_4.toString()).toNotNullable() -> LEVEL_TYPE_4
        user?.levelId?.startsWith(LEVEL_TYPE_5.toString()).toNotNullable() -> LEVEL_TYPE_5
        else -> LEVEL_TYPE_0
    }

    val freeShipping: String =
            if (product.freeShipping) {
                context?.getString(R.string.txt_free_shipping).toNotNullable()
            } else {
                String.empty()
            }

    val condition = context?.getString(
            if (product.condition == Product.CONDITION_TYPE_NEW) {
                R.string.txt_new
            } else {
                R.string.txt_used
            }
        ).toNotNullable()
}