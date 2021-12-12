package com.manubla.freemarket.view.provider

import android.view.ViewGroup
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.data.model.business.Product
import com.manubla.freemarket.data.model.business.State
import com.manubla.freemarket.data.model.business.User
import com.manubla.freemarket.data.model.result.SearchResult
import com.manubla.freemarket.view.adapter.NavigateCallback
import com.manubla.freemarket.view.viewholder.ErrorViewHolder
import com.manubla.freemarket.view.viewholder.PagingProductViewHolder
import com.manubla.freemarket.view.viewholder.UnknownViewHolder
import com.manubla.freemarket.view.viewholder.base.BaseModelViewHolder

class ViewHolderModelProvider<M : Model> {

    fun create(parent: ViewGroup, viewType: Int): BaseModelViewHolder<M> {
        return when(viewType) {
            VIEW_TYPE_PRODUCT -> PagingProductViewHolder.from(parent)
            VIEW_TYPE_SEARCH_RESULT -> ErrorViewHolder.from(parent)
            else -> UnknownViewHolder.from(parent)
        }
    }

    fun bind(holder: BaseModelViewHolder<M>, item: M?, navigateCallback: NavigateCallback?) {
        holder.bind(item, navigateCallback)
    }

    fun getViewType(item: M?): Int {
        return item?.let {
            when(it) {
                is Product -> VIEW_TYPE_PRODUCT
                is User -> VIEW_TYPE_USER
                is State -> VIEW_TYPE_STATE
                is SearchResult -> VIEW_TYPE_SEARCH_RESULT
                else -> VIEW_TYPE_DEFAULT
            }
        } ?: VIEW_TYPE_DEFAULT
    }

}