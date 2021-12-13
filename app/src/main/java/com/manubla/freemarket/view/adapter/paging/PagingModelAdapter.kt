package com.manubla.freemarket.view.adapter.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.view.provider.ViewHolderModelProvider
import com.manubla.freemarket.view.viewholder.base.BaseModelViewHolder

class PagingModelAdapter<M : Model>(
    diffCallback: DiffModels<M>,
    private val viewHolderProvider: ViewHolderModelProvider<M>
) : PagingDataAdapter<M, BaseModelViewHolder<M>>(diffCallback) {

    var navigateCallback: NavigateCallback? = null

    override fun getItemViewType(position: Int): Int {
        return viewHolderProvider.getViewType(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseModelViewHolder<M> {
        return viewHolderProvider.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseModelViewHolder<M>, position: Int) {
        viewHolderProvider.bind(holder, getItem(position), navigateCallback)
    }

}