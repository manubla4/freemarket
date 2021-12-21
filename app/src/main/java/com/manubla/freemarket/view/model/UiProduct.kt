package com.manubla.freemarket.view.model

import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.model.business.SellerReputation
import com.manubla.freemarket.data.model.business.State
import com.manubla.freemarket.data.model.business.User
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery
import com.manubla.freemarket.extension.toNotNullable
import java.util.*

class UiProduct(product: ProductCurrencyQuery,
                user: User? = null,
                state: State? = null
) {
    val title: String = product.title.toNotNullable()

    val thumbnail: String = product.thumbnail.toNotNullable()

    val displayPrice = "${product.currency ?: DEFAULT_CURRENCY} ${product.price.toNotNullable().toInt()}"

    val warranty: String = product.warranty.toNotNullable().replace(
        PLURAL_1_YEAR_WARRANTY, SINGULAR_1_YEAR_WARRANTY)

    val hasWarranty: Boolean = warranty.isNotBlank()

    val userName: String = user?.nickname.toNotNullable().toUpperCase(Locale.getDefault())

    val userAddress: String = "${user?.city}, ${state?.name}"

    val pictures: List<String> = product.pictures?.map { it.url } ?: emptyList()

    val isPlatinumUser: Boolean = user?.powerSellerStatus == SellerReputation.STATUS_TYPE_PLATINIUM

    val userLevel: UserLevel = when {
        user?.levelId?.startsWith(1.toString()).toNotNullable() -> UserLevel.LEVEL_TYPE_1
        user?.levelId?.startsWith(2.toString()).toNotNullable() -> UserLevel.LEVEL_TYPE_2
        user?.levelId?.startsWith(3.toString()).toNotNullable() -> UserLevel.LEVEL_TYPE_3
        user?.levelId?.startsWith(4.toString()).toNotNullable() -> UserLevel.LEVEL_TYPE_4
        user?.levelId?.startsWith(5.toString()).toNotNullable() -> UserLevel.LEVEL_TYPE_5
        else -> UserLevel.LEVEL_TYPE_0
    }

    val isFreeShipping: Boolean = product.freeShipping.toNotNullable()

    val condition =
        if (product.condition == Product.CONDITION_TYPE_NEW) {
            TEXT_NEW
        } else {
            TEXT_USED
        }

    companion object {
        internal const val DEFAULT_CURRENCY = "$"
        private const val PLURAL_1_YEAR_WARRANTY = "1 años"
        private const val SINGULAR_1_YEAR_WARRANTY = "1 año"
        private const val TEXT_NEW = "Nuevo"
        private const val TEXT_USED = "Usado"
    }
}