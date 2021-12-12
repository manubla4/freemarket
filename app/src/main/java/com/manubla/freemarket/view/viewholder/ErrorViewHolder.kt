package com.manubla.freemarket.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.manubla.freemarket.R
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.data.model.result.SearchResult
import com.manubla.freemarket.databinding.ViewEmptyErrorItemBinding
import com.manubla.freemarket.view.adapter.NavigateCallback
import com.manubla.freemarket.view.viewholder.base.BaseModelViewHolder

class ErrorViewHolder<M: Model>(
    private val viewBinding: ViewEmptyErrorItemBinding
): BaseModelViewHolder<M>(viewBinding) {

    override fun bind(model: M?, navigateCallback: NavigateCallback?) {
        model.asSearchResult {
            when(it.type) {
                SearchResult.STATE_EMPTY -> {
                    viewBinding.message = viewBinding.root.context.getString(R.string.txt_empty_search)
                }
                SearchResult.STATE_ERROR -> {
                    viewBinding.message = viewBinding.root.context.getString(R.string.txt_error_search)
                }
            }
        }
    }

    private fun M?.asSearchResult(callback: (product: SearchResult) -> Unit) {
        val searchResult = this as SearchResult?
        searchResult?.let {
            callback(it)
        }
    }

    companion object {
        fun <M: Model> from(parent: ViewGroup): BaseModelViewHolder<M> {
            val inflater = LayoutInflater.from(parent.context)
            val viewBinding = ViewEmptyErrorItemBinding.inflate(inflater, parent, false)
            return ErrorViewHolder(viewBinding)
        }

    }
}