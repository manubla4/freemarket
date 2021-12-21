package com.manubla.freemarket.view.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.manubla.freemarket.R
import com.manubla.freemarket.databinding.FragmentDetailBinding
import com.manubla.freemarket.extension.toNotNullable
import com.manubla.freemarket.view.adapter.image.ImageAdapter
import com.manubla.freemarket.view.event.DetailState
import com.manubla.freemarket.view.extension.visible
import com.manubla.freemarket.view.extension.visibleIf
import com.manubla.freemarket.view.fragment.base.ViewDataBindingFragment
import com.manubla.freemarket.view.model.UiProduct
import com.manubla.freemarket.view.viewmodel.DetailViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : ViewDataBindingFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModel()
    private val imageAdapter: ImageAdapter by inject()

    override fun onViewCreated(viewDataBinding: FragmentDetailBinding, savedInstanceState: Bundle?) {
        super.onViewCreated(viewDataBinding, savedInstanceState)
        val data = arguments?.getString(ARG_DATA).toNotNullable()
        viewDataBinding.recyclerView.setup()
        setObservers()
        viewModel.fetchProduct(data)
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is DetailState.Loading -> showProgress(it.loading)
                is DetailState.Error -> showError()
                is DetailState.Data -> onDataReceived(it.uiProduct)
            }
        })
    }

    private fun showProgress(show: Boolean) {
        viewDataBinding.layoutLoading.visibleIf(show)
    }

    private fun showError() {
        viewDataBinding.layoutError.visible()
        showProgress(false)
    }

    private fun onDataReceived(uiProduct: UiProduct) {
        viewDataBinding.uiProduct = uiProduct
        viewDataBinding.executePendingBindings()
        showProgress(false)
    }

    private fun RecyclerView.setup() {
        this.adapter = imageAdapter
        this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        this.setHasFixedSize(true)
        PagerSnapHelper().attachToRecyclerView(this)
    }

}