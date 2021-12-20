package com.manubla.freemarket.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.manubla.freemarket.databinding.PagingErrorItemBinding
import com.manubla.freemarket.view.extension.invisibleIf

class PagingStateViewHolder(
    private val viewBinding: PagingErrorItemBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(loadState: LoadState, retry: () -> Unit = {}) {
        viewBinding.progressBar.invisibleIf(loadState !is LoadState.Loading)
        viewBinding.textMessageErrorPaging.invisibleIf(loadState !is LoadState.Error)
        viewBinding.buttonActionRetryPaging.invisibleIf(loadState !is LoadState.Error)
        viewBinding.buttonActionRetryPaging.setOnClickListener {
            retry.invoke()
        }
    }

    companion object {
        fun from(parent: ViewGroup): PagingStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val viewBinding = PagingErrorItemBinding.inflate(inflater, parent, false)
            return PagingStateViewHolder(viewBinding)
        }
    }
}