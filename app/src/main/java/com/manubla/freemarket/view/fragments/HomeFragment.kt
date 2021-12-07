package com.manubla.freemarket.view.fragments

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.manubla.freemarket.R
import com.manubla.freemarket.databinding.FragmentHomeBinding
import com.manubla.freemarket.utils.invisibleIf
import com.manubla.freemarket.utils.visibleIf
import com.manubla.freemarket.view.adapters.PagingStateAdapter
import com.manubla.freemarket.view.alias.PagingAdapter
import com.manubla.freemarket.view.viewmodels.HomeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: ViewBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()
    private val adapter: PagingAdapter by inject()
    private val loadingAdapter: PagingStateAdapter by lazy {
        PagingStateAdapter {
            adapter.retry()
        }
    }
    private lateinit var navController: NavController

    override fun onViewCreated(viewDataBinding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(viewDataBinding.root)
        setupSwipeToRefresh(viewDataBinding.swipeRefreshLayout)
        setupRecyclerView(viewDataBinding.recyclerView)
        setupAdapter()
    }

    private fun setupSwipeToRefresh(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        with(recyclerView) {
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = this@HomeFragment.adapter.withLoadStateFooter(loadingAdapter)
            this.layoutManager = linearLayoutManager
            this.setItemViewCacheSize(ITEM_CACHED)
        }
    }

    private fun setupAdapter() {
        adapter.addLoadStateListener {
            val isRefreshing = it.refresh is LoadState.Loading
            viewBinding.recyclerView.invisibleIf(isRefreshing)
            showProgress(isRefreshing)
        }
    }

    private fun showProgress(show: Boolean) {
        viewBinding.progressBar.visibleIf(show)
    }

    companion object {
        private const val ITEM_CACHED = 10
    }
}