package com.manubla.freemarket.view.viewholder.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.manubla.freemarket.data.model.base.Model

abstract class BaseModelViewHolder<M: Model>(
    viewBinding: ViewDataBinding
): RecyclerView.ViewHolder(viewBinding.root) {
    abstract fun bind(model: M)
}