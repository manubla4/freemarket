package com.manubla.freemarket.view.adapter.paging

import androidx.recyclerview.widget.DiffUtil
import com.manubla.freemarket.data.model.base.Model

class DiffModels<M : Model>: DiffUtil.ItemCallback<M>() {

    override fun areItemsTheSame(oldItem: M, newItem: M): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: M, newItem: M): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

}