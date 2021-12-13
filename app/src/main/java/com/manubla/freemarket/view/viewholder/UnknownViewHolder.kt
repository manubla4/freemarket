package com.manubla.freemarket.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.manubla.freemarket.BuildConfig
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.databinding.ViewUnknownItemBinding
import com.manubla.freemarket.view.adapter.paging.NavigateCallback
import com.manubla.freemarket.view.viewholder.base.BaseModelViewHolder

class UnknownViewHolder<M: Model>(
    private val viewBinding: ViewUnknownItemBinding
): BaseModelViewHolder<M>(viewBinding) {

    override fun bind(model: M?, navigateCallback: NavigateCallback?) {
        viewBinding.isDebug = BuildConfig.DEBUG
        viewBinding.executePendingBindings()
    }

    companion object {
        fun <M: Model> from(parent: ViewGroup): BaseModelViewHolder<M> {
            val inflater = LayoutInflater.from(parent.context)
            val viewBinding = ViewUnknownItemBinding.inflate(inflater, parent, false)
            return UnknownViewHolder(viewBinding)
        }
    }
}