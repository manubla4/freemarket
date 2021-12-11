package com.manubla.freemarket.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.databinding.ViewPagingProductItemBinding
import com.manubla.freemarket.view.viewholder.base.BaseModelViewHolder

class PagingProductViewHolder<M: Model>(
    private val viewBinding: ViewPagingProductItemBinding
): BaseModelViewHolder<M>(viewBinding) {

    override fun bind(model: M) {
        model.asProduct {
            viewBinding.product = it
        }
    }

    private fun M.asProduct(callback: (product: Product) -> Unit) {
        val product = this as Product?
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
