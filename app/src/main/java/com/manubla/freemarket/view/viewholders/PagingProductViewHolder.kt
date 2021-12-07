package com.manubla.freemarket.view.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.databinding.PagingProductItemBinding

class PagingProductViewHolder(private val viewBinding: PagingProductItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(product: Product?) {
        product?.let {
            viewBinding.product = it
        }
    }

    companion object {
        fun from(parent: ViewGroup): PagingProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val viewBinding = PagingProductItemBinding.inflate(inflater, parent, false)
            return PagingProductViewHolder(viewBinding)
        }
    }
}