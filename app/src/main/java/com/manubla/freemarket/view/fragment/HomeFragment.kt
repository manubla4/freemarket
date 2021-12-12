package com.manubla.freemarket.view.fragment

import android.os.Bundle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.manubla.freemarket.R
import com.manubla.freemarket.databinding.FragmentHomeBinding
import com.manubla.freemarket.view.adapter.PagingStateAdapter
import com.manubla.freemarket.view.alias.PagingAdapter
import com.manubla.freemarket.view.event.HomeState
import com.manubla.freemarket.view.extension.invisibleIf
import com.manubla.freemarket.view.extension.setOnSearch
import com.manubla.freemarket.view.extension.visibleIf
import com.manubla.freemarket.view.fragment.base.ViewDataBindingFragment
import com.manubla.freemarket.view.viewmodel.HomeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: ViewDataBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()
    private val adapter: PagingAdapter by inject()
    private val loadingAdapter: PagingStateAdapter by lazy {
        PagingStateAdapter {
            adapter.retry()
        }
    }

    override fun onViewCreated(viewDataBinding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        super.onViewCreated(viewDataBinding, savedInstanceState)
        setupViews(viewDataBinding)
        setObservers()
    }

    private fun setupViews(viewDataBinding: FragmentHomeBinding) {
        viewDataBinding.layoutSearch.inputTxtSearch.setOnSearch {
            viewModel.fetchPagingData(it)
        }
        viewDataBinding.swipeRefreshLayout.setup()
        viewDataBinding.recyclerView.setup()
        adapter.setup()
    }

    private fun SwipeRefreshLayout.setup() {
        this.setOnRefreshListener {
            isRefreshing = false
            adapter.refresh()
        }
    }

    private fun RecyclerView.setup() {
        this.adapter = this@HomeFragment.adapter.withLoadStateFooter(loadingAdapter)
        this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        this.setItemViewCacheSize(ITEM_CACHED)
    }

    private fun PagingAdapter.setup() {
        addLoadStateListener {
            val isRefreshing = it.refresh is LoadState.Loading
            viewDataBinding.recyclerView.invisibleIf(isRefreshing)
            showProgress(isRefreshing)
        }
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is HomeState.Loading -> showProgress(it.loading)
                is HomeState.Data -> adapter.submitData(lifecycle, it.pagingData)
            }
        })
    }

    private fun showProgress(show: Boolean = true) {
        viewDataBinding.progressBar.visibleIf(show)
    }

    companion object {
        private const val ITEM_CACHED = 10
    }

}
