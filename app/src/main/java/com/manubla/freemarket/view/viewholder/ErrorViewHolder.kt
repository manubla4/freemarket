package com.manubla.freemarket.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.data.model.result.SearchResult
import com.manubla.freemarket.databinding.ViewEmptyErrorItemBinding
import com.manubla.freemarket.view.viewholder.base.BaseModelViewHolder

class ErrorViewHolder<M: Model>(
    private val viewBinding: ViewEmptyErrorItemBinding
): BaseModelViewHolder<M>(viewBinding) {

    override fun bind(model: M?) {
        model.asSearchResult {
            when(it.type) {
                SearchResult.STATE_EMPTY -> {
                    viewBinding.message = MESSAGE_EMPTY
                }
                SearchResult.STATE_ERROR -> {
                    viewBinding.message = MESSAGE_ERROR
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

        const val MESSAGE_EMPTY = "Lo sentimos, pero no encontramos resultados para tu búsqueda"
        const val MESSAGE_ERROR = "Lo sentimos, pero ha ocurrido un problema en tu búsqueda"
    }
}