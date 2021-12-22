package com.manubla.freemarket.view.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputEditText
import com.manubla.freemarket.R
import com.manubla.freemarket.databinding.FragmentHomeBinding
import com.manubla.freemarket.extension.isNull
import com.manubla.freemarket.extension.zero
import com.manubla.freemarket.view.adapter.paging.PagingStateAdapter
import com.manubla.freemarket.view.alias.PagingAdapter
import com.manubla.freemarket.view.callback.NavigateCallback
import com.manubla.freemarket.view.enum.Destination
import com.manubla.freemarket.view.event.HomeState
import com.manubla.freemarket.view.extension.*
import com.manubla.freemarket.view.fragment.base.ViewDataBindingFragment
import com.manubla.freemarket.view.util.hideKeyboard
import com.manubla.freemarket.view.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalPagingApi
class HomeFragment: ViewDataBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()
    private val adapter: PagingAdapter by inject()
    private var restoreQuery: String? = null
    private var currentQuery: String? = null
    private var callback: NavigateCallback? = null
    private val loadingAdapter: PagingStateAdapter by lazy {
        PagingStateAdapter {
            adapter.retry()
        }
    }

    override fun onViewCreated(viewDataBinding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        super.onViewCreated(viewDataBinding, savedInstanceState)
        setFragmentResultListener(DetailFragment.DETAIL_FRAGMENT_REQUEST) { key, bundle ->
            restoreQuery = bundle.getString(ARG_QUERY)
            currentQuery = restoreQuery
        }
        savedInstanceState?.let {
            restoreQuery = it.getString(ARG_QUERY)
            currentQuery = restoreQuery
        }
        setupViews(viewDataBinding)
        setObservers()
    }

    private fun setupViews(viewDataBinding: FragmentHomeBinding) {
        viewDataBinding.layoutSearch.inputTxtSearch.setup()
        viewDataBinding.swipeRefreshLayout.setup()
        viewDataBinding.recyclerView.setup()
        adapter.setup()
    }

    private fun onSearch(query: String) {
        hideKeyboard(context, viewDataBinding.rootContainer)
        viewDataBinding.layoutErrorContainer.gone()
        viewDataBinding.swipeRefreshLayout.visible()
        viewModel.fetchPagingData(query)
    }

    private fun TextInputEditText.setup() {
        setOnFocusChangeListener { _, _ ->
            viewDataBinding.imgLogo.gone()
        }
        setOnSearch {
            currentQuery = it
            onSearch(it)
        }
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
    }

    private fun PagingAdapter.setup() {
        callback = this@HomeFragment
        navigateCallback = callback
        addLoadStateListener {
            val isRefreshing = it.mediator?.refresh is LoadState.Loading
            viewDataBinding.swipeRefreshLayout.invisibleIf(isRefreshing)
            showProgress(isRefreshing)
        }
        setupAdapterLoadStateListening()
    }

    private fun setupAdapterLoadStateListening() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    viewDataBinding.recyclerView.scrollToPosition(Int.zero())
                }
        }
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner, { state ->
            if (restoreQuery.isNull()) {
                when (state) {
                    is HomeState.Loading -> showProgress(state.loading)
                    is HomeState.Data -> adapter.submitData(lifecycle, state.pagingData)
                    is HomeState.Empty -> showEmpty()
                    is HomeState.Error -> showError()
                }
            }
            restoreQuery?.let { restoreFragment(it) }
        })
    }

    private fun restoreFragment(query: String) {
        viewDataBinding.imgLogo.gone()
        onSearch(query)
        this.restoreQuery = null
    }

    private fun showError() {
        showProgress(false)
        with(viewDataBinding) {
            swipeRefreshLayout.gone()
            layoutError.drawable = R.drawable.ic_error
            layoutError.message = context?.getString(R.string.txt_error)
            layoutError.executePendingBindings()
            layoutErrorContainer.visible()
        }
    }

    private fun showEmpty() {
        showProgress(false)
        with(viewDataBinding) {
            swipeRefreshLayout.gone()
            layoutError.drawable = R.drawable.ic_search
            layoutError.message = context?.getString(R.string.txt_empty_search)
            layoutError.executePendingBindings()
            layoutErrorContainer.visible()
        }
    }

    private fun showProgress(show: Boolean) {
        viewDataBinding.progressBar.visibleIf(show)
    }

    override fun onNavigate(destination: Destination, data: String) {
        val bundle = bundleOf(
            ARG_DATA to data,
            ARG_QUERY to currentQuery
        )
        if (destination == Destination.DESTINATION_DETAIL) {
            navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ARG_QUERY, currentQuery)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroy()
        callback = null
    }
}