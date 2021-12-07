package com.manubla.freemarket.view.adapters

import androidx.recyclerview.widget.DiffUtil
import com.manubla.freemarket.data.model.Product

class DiffProducts<M : Product> private constructor() : DiffUtil.ItemCallback<M>() {

    override fun areItemsTheSame(oldItem: M, newItem: M): Boolean {
        return oldItem.id == newItem.id && oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: M, newItem: M): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }

    companion object {
        fun <M : Product> diffUtil() = DiffProducts<M>()
    }
}