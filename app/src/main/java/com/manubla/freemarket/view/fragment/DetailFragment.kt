package com.manubla.freemarket.view.fragment

import android.os.Bundle
import com.manubla.freemarket.R
import com.manubla.freemarket.databinding.FragmentDetailBinding
import com.manubla.freemarket.extension.toNotNullable
import com.manubla.freemarket.view.event.DetailState
import com.manubla.freemarket.view.event.HomeState
import com.manubla.freemarket.view.extension.visibleIf
import com.manubla.freemarket.view.fragment.base.ViewDataBindingFragment
import com.manubla.freemarket.view.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : ViewDataBindingFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = arguments?.getString(ARG_DATA).toNotNullable()
        viewModel.fetchProduct(data)
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is DetailState.Loading -> showProgress(it.loading)
                is DetailState.Data -> {

                }
            }
        })
    }

    private fun showProgress(show: Boolean = true) {
        viewDataBinding.progressBar.visibleIf(show)
    }

}