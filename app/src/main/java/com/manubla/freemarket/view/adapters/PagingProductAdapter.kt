package com.manubla.freemarket.view.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.manubla.freemarket.data.model.Product
import com.manubla.freemarket.view.viewholders.PagingProductViewHolder

class PagingProductAdapter<S : Product>(
    diffCallback: DiffUtil.ItemCallback<S>
) : PagingDataAdapter<S, PagingProductViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingProductViewHolder {
        return PagingProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PagingProductViewHolder, position: Int) {
        val product = getItem(position) as Product?
        holder.bind(product)
    }
}