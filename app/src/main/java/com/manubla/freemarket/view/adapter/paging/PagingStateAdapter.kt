package com.manubla.freemarket.view.adapter.paging

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.manubla.freemarket.view.viewholder.PagingStateViewHolder

class PagingStateAdapter(
    private val retry: () -> Unit = {}
) : LoadStateAdapter<PagingStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PagingStateViewHolder {
        return PagingStateViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PagingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }
}