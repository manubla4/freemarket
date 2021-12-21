package com.manubla.freemarket.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.data.source.storage.query.ProductCurrencyQuery
import com.manubla.freemarket.databinding.ViewPagingProductItemBinding
import com.manubla.freemarket.view.callback.NavigateCallback
import com.manubla.freemarket.view.model.UiProduct
import com.manubla.freemarket.view.viewholder.base.BaseModelViewHolder

class PagingProductViewHolder<M: Model>(
    private val viewBinding: ViewPagingProductItemBinding
): BaseModelViewHolder<M>(viewBinding) {

    override fun bind(model: M?, navigateCallback: NavigateCallback?) {
        model.asProductQuery { product ->
            viewBinding.uiProduct = UiProduct(product)
            viewBinding.clickableFrame.setOnClickListener {
                navigateCallback?.onNavigate(NavigateCallback.DESTINATION_DETAIL, product.id)
            }
            viewBinding.executePendingBindings()
        }
    }

    private fun M?.asProductQuery(
        callback: (product: ProductCurrencyQuery) -> Unit
    ) {
        val product = this as ProductCurrencyQuery?
        product?.let {
            callback(it)
        }
    }

    companion object {
        fun <M: Model> from(parent: ViewGroup): BaseModelViewHolder<M> {
            val inflater = LayoutInflater.from(parent.context)
            val viewBinding = ViewPagingProductItemBinding.inflate(inflater, parent, false)
            return PagingProductViewHolder(viewBinding)
        }
    }
}
