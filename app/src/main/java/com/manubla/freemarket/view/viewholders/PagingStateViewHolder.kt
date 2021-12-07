package com.manubla.freemarket.view.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.manubla.freemarket.databinding.PagingErrorItemBinding

class PagingStateViewHolder(private val viewBinding: PagingErrorItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(loadState: LoadState, retry: () -> Unit = {}) {
        viewBinding.progressBar.isVisible = loadState is LoadState.Loading
        viewBinding.textMessageErrorPaging.isVisible = loadState is LoadState.Error
        viewBinding.buttonActionRetryPaging.isVisible = loadState is LoadState.Error
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